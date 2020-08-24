//package com.example.myapplication;
//
//import androidx.annotation.NonNull;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.Observable;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//
//public class temp {
//    public static final String NEWS_BASE_URL = "https://www.hqck.net/";
//    static String transUrlHttps(String url){
//        return url.replace("http","https");
//    }
//
//    public static Observable<List<NewsLinkModel>> getNewsUrlList(){
//        List<NewsLinkModel> imageList = new ArrayList<>();
//        Retrofit retrofit = getRetrofitInstance(NEWS_BASE_URL,htmlConverter());
//
//        // 步骤5：创建 网络请求接口 的实例
//        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//        //获取homepage html
//        return request.getHtmlByNetAsy("")
//                .subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
////                .observeOn(Schedulers.io())
//                .map(str -> parseNewsHtml(str));
//    }
//    private static Retrofit getRetrofitInstance(String url, Converter.Factory converter){
//        return new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(converter)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//    }
//
//    private static Converter.Factory htmlConverter(){
//        return new Converter.Factory() {
//            @Override
//            public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//                return (Converter<ResponseBody, String>) value -> {
//                    byte[] bty = value.bytes();
//                    return new String(bty, "gb2312");
//                };
//            }
//        };
//    }
//
//    public static Observable<String> getImageUrlList(String newsUrl){
//        Retrofit retrofit = getRetrofitInstance(NEWS_BASE_URL,htmlConverter());
//        // 步骤5：创建 网络请求接口 的实例
//        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//        //获取homepage html
//        List<String> imageUrlList = new ArrayList<>();
//        return request.getHtmlByNetAsy(newsUrl)
//                .subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
//                .map(str -> parseNewsPageHtml(str,newsUrl))
//                .flatMapIterable(it->it)
//                .map(str->parseImagesLink((request.getHtmlByNetSyn(str).execute().body())));
////                .collectInto(imageUrlList, (strings, s) -> strings.add(s))
////                .toObservable();
//    }
//
////    private Observable<Bitmap> downloadImage(String url, boolean isGlobal){
////        Retrofit retrofit = getRetrofitInstance(NEWS_BASE_URL, GsonConverterFactory.create());
////        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
////        String rootPath = Objects.requireNonNull(getExternalFilesDir("")).toString();
////        return request .downloadPicFromNet(url)
////                .subscribeOn(Schedulers.io())
////                .observeOn(Schedulers.newThread())
////                .map(responseBody -> {
////                    Bitmap bitmap = null;
////                    byte[] bys;
////                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
//////                        Log.e("date",sdf.format(new Date()));
////                    String filePath = rootPath+sdf.format(new Date());
////                    if(isGlobal) filePath+="/huanqiu/";
////                    else filePath+="/cankao/";
////                    Log.e("path",filePath);
////                    try {
////                        bys = responseBody.bytes();
////                        bitmap = BitmapFactory.decodeByteArray(bys,0,bys.length);
////                        Log.e("bytelength", String.valueOf(bys.length));
////                        if(bitmap == null){
////                            Log.e("empty","image");
////                        }
////                        saveBitmap(bitmap,filePath,url.substring(url.lastIndexOf('/')));
////                    } catch (IOException | OutOfMemoryError e) {
////                        e.printStackTrace();
////                    }
////                    return bitmap;
////                });
////
////    }
//
//    public static String parseImagesLink(String resHtml){
////        Log.e("hello","hello");
//        Document doc = Jsoup.parse(resHtml);
//        //获取报纸图片信息
//        Elements image_doc = doc.getElementsByTag("img");
//        String imgsrc = image_doc.get(0).attr("src");
//
//        Elements title_doc = doc.getElementsByTag("meta");
//        String title = title_doc.get(0).attr("content");
//
////        Log.i("warmming","abc"+imgsrc);
//        return imgsrc;
//    }
//    public static List<String> parseNewsPageHtml(@NonNull String resHtml, @NonNull String newsUrl){
//        List<String> newsDetailList = new ArrayList<>();
//
//        Document doc = Jsoup.parse(resHtml);
//        Elements page = doc.getElementsByTag("option");
//        //当期报纸页数
//        int num = page.size()/2;
////        Log.e("imgnums", String.valueOf(num));
//        //拼接报纸页面连接
//        String htmllink = newsUrl;
//        String htmlhead =   htmllink.substring(0,htmllink.lastIndexOf("/")+1);
//        //   String htmlfoot = htmllink.substring(htmllink.length()-5,htmllink.length());
//
//        for(int j = 1 ; j <= num ; j ++){
//            String link = htmlhead+page.get(j-1).attr("value");
//            newsDetailList.add(link);
//        }
//        return newsDetailList;
//    }
//
//    private static List<NewsLinkModel> parseNewsHtml(@NonNull String newsHtml) {
////        Log.e("isNull", newsHtml);
//        List<NewsLinkModel> mNewsList = new ArrayList<>();
//        Document doc = Jsoup.parse(newsHtml);
//        //获取左侧报纸日期元素信息
//        Elements html_list = doc.getElementsByClass("jBoxBody");
//        html_list = html_list.get(0).getElementsByTag("li");
//        //Log.e("sss",html_list.toString());
//        for (int i = 0; i < html_list.size(); i++) {
//
//            //Log.e("suffix",html_list.get(i).toString());
//            //获取链接、颜色、当日标题
//            String pagelink = html_list.get(i).getElementsByTag("a").attr("href");//?
//            String pagetitle = html_list.get(i).getElementsByTag("a").text();
//            NewsLinkModel newslink = new NewsLinkModel(pagetitle,pagelink);
////            Log.e("hello", pagelink + "  " + pagetitle);
//
//
//            mNewsList.add(newslink);
//        }
//        return mNewsList;
//    }
//}
