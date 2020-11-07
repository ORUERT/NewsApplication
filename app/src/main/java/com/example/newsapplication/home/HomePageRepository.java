package com.example.newsapplication.home;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.newsapplication.util.GetRequest_Interface;
import com.example.newsapplication.home.model.NewsLinkModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HomePageRepository extends AbsNewsRepository {

    /**
     * get news from three source
     * @return
     */
    public Observable<List<NewsLinkModel>> getNewsUrlList(){
        Retrofit retrofit = getRetrofitInstance(getmBaseUrl(),htmlConverter());
        // 步骤5：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        //获取homepage html
        return request.getHtmlByNetAsy("")
                .subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .map(s -> {
                    switch(getmSource()){
                        case 1:
                            return parseHtNewsHtml(s);
                        case 2:
                            return parseJwNewsHtml(s);
                        default:
                            return parseJdNewsHtml(s);
                    }
                });
    }

    /**
     * choose converter
     * @param url
     * @param converter
     * @return
     */
    private Retrofit getRetrofitInstance(String url, Converter.Factory converter){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private Converter.Factory htmlConverter(){
        return new Converter.Factory() {
            @Override
            public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                return (Converter<ResponseBody, String>) value -> {
                    byte[] bty = value.bytes();
                    return new String(bty, "gb2312");
                };
            }
        };
    }

    /**
     *get first page
     * @param newsUrl
     * @return
     */
    public Observable<String> getImageUrlList(String newsUrl){
        Retrofit retrofit = getRetrofitInstance(getmBaseUrl(),htmlConverter());
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        //get homepage html
        //use multi th cause random order
        return request.getHtmlByNetAsy(newsUrl)
                .subscribeOn(Schedulers.io())
                .map(str -> parseNewsPageHtml(str,newsUrl))
                .concatMapIterable(it->it)
                .map(str->parseImagesLink((request.getHtmlByNetSyn(str).execute().body())));
//                .doOnNext(it->Log.i("sky",it.toString()));
//                .doOnNext(it->Log.e("c",it+" "+newsUrl));

    }
    /**
     * downloadImage
     */
//    private Observable<Bitmap> downloadImage(String url, boolean isGlobal){
//        Retrofit retrofit = getRetrofitInstance(NEWS_BASE_URL, GsonConverterFactory.create());
//        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//        String rootPath = Objects.requireNonNull(getExternalFilesDir("")).toString();
//        return request .downloadPicFromNet(url)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .map(responseBody -> {
//                    Bitmap bitmap = null;
//                    byte[] bys;
//                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
////                        Log.e("date",sdf.format(new Date()));
//                    String filePath = rootPath+sdf.format(new Date());
//                    if(isGlobal) filePath+="/huanqiu/";
//                    else filePath+="/cankao/";
//                    Log.e("path",filePath);
//                    try {
//                        bys = responseBody.bytes();
//                        bitmap = BitmapFactory.decodeByteArray(bys,0,bys.length);
//                        Log.e("bytelength", String.valueOf(bys.length));
//                        if(bitmap == null){
//                            Log.e("empty","image");
//                        }
//                        saveBitmap(bitmap,filePath,url.substring(url.lastIndexOf('/')));
//                    } catch (IOException | OutOfMemoryError e) {
//                        e.printStackTrace();
//                    }
//                    return bitmap;
//                });
//
//    }

    /**
     * convert detail page html to image url
     * @param resHtml
     * @return
     */
    public static String parseImagesLink(String resHtml){
        Document doc = Jsoup.parse(resHtml);
        //获取报纸图片信息
        Elements image_doc = doc.getElementsByTag("img");
        String imgsrc = image_doc.get(0).attr("src");
        return imgsrc;
    }

    /**
     * get detail page url list
     * @param resHtml
     * @param newsUrl
     * @return
     */
    public static List<String> parseNewsPageHtml(@NonNull String resHtml, @NonNull String newsUrl){
        List<String> newsDetailList = new ArrayList<>();

        Document doc = Jsoup.parse(resHtml);
        Elements page = doc.getElementsByTag("option");
        //get numbers of pages
        int num = page.size()/2;
        //conbine baseurl and tail
        String htmllink = newsUrl;
        String htmlhead =   htmllink.substring(0,htmllink.lastIndexOf("/")+1);

        for(int j = 1 ; j <= num ; j ++){
            String link = htmlhead+page.get(j-1).attr("value");
            newsDetailList.add(link);
        }
        return newsDetailList;
    }

    /**
     * convert jiuwei site html to List newslinkmodel
     * @param newsHtml
     * @return
     */
    public List<NewsLinkModel> parseJwNewsHtml(@NonNull String newsHtml) {
//        Log.e("isNull", newsHtml);
        List<NewsLinkModel> mNewsList = new ArrayList<>();
        Document doc = Jsoup.parse(newsHtml);
        Elements html_list = doc.getElementsByClass("jBoxBody");
        html_list = html_list.get(0).getElementsByTag("li");
        //Log.e("sss",html_list.toString());
        for (int i = 0; i < html_list.size(); i++) {
            //Log.e("suffix",html_list.get(i).toString());

            //get title and url
            String pagelink = html_list.get(i).getElementsByTag("a").attr("href");//?
            String pagetitle = stringFilter(html_list.get(i).getElementsByTag("a").text());
            NewsLinkModel newslink = new NewsLinkModel(pagetitle,(pagelink));
//            Log.e("hello", pagelink + "  " + pagetitle);
            mNewsList.add(newslink);
        }
        return mNewsList;
    }

    /**
     * convert haitian html to List newslinkmodel
     * @param newsHtml
     * @return
     */
    public List<NewsLinkModel> parseHtNewsHtml(@NonNull String newsHtml) {
        List<NewsLinkModel> mNewsList = new ArrayList<>();
        Document doc = Jsoup.parse(newsHtml);
        Element mElement = doc.getElementById("mil_zhong001_left2_list");
        Elements mElements = mElement.getElementsByTag("a");
        for(int i = 0 ; i < mElements.size() ; i ++){
            String pagelink = mBaseUrl+mElements.get(i).attr("href");//?
            String pagetitle = stringFilter(mElements.get(i).attr("title"));
            NewsLinkModel newsLinkModel = new NewsLinkModel(pagetitle,(pagelink));
            mNewsList.add(newsLinkModel);

        }
        return mNewsList;
    }

    /**
     * convert jiaodu site html to List newslinkmodel
     * @param newsHtml
     * @return
     */
    public List<NewsLinkModel> parseJdNewsHtml(@NonNull String newsHtml) {
        List<NewsLinkModel> mNewsList = new ArrayList<>();
        Document doc = Jsoup.parse(newsHtml);
        Elements mElement = doc.getElementsByClass("zxgx-nr");
//        Log.e("asd", String.valueOf(mElement.size()));
        mElement = mElement.get(0).getElementsByTag("a");
//        Log.e("asd", String.valueOf(mElement.size()));
        for(int i = 0 ; i < mElement.size() ; i ++){
            String pagelink = mBaseUrl+mElement.get(i).attr("href");//?
            String pagetitle = stringFilter(mElement.get(i).text());
            NewsLinkModel newsLinkModel = new NewsLinkModel(pagetitle,(pagelink));
            mNewsList.add(newsLinkModel);
        }
        return mNewsList;
    }
    private String stringFilter(String s){
        return s.replace("在线阅读","").replace(" ","").replace("电子版","");
    }
}
