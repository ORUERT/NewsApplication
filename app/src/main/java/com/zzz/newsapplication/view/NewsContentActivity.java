package com.zzz.newsapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jelly.mango.MultiplexImage;
import com.zzz.newsapplication.R;
import com.zzz.newsapplication.adapter.ImageRecyclerAdapter;
import com.zzz.newsapplication.adapter.OnRecyclerItemClickListener;
import com.zzz.newsapplication.bean.CommonException;
import com.zzz.newsapplication.site.DataUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.jelly.mango.Mango.position;

public class NewsContentActivity extends AppCompatActivity implements ImageView {

    private View view;
    private RecyclerView rv;

    private List<MultiplexImage> images =new ArrayList<MultiplexImage>();
    private ImageRecyclerAdapter adapter;
    //    private Imagepresenter presenter;
    private String newsLink;
    private String newsTitle;
    private boolean complete;
    private int newsPos;

    public static void actionStart(Context context , String newsTitle , String newsLink,int newPos,boolean complete){
        Intent intent = new Intent(context , NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_link",newsLink);
        intent.putExtra("news_position",newPos);
        intent.putExtra("news_complete",complete);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        newsTitle = getIntent().getStringExtra("news_title");

        newsLink = getIntent().getStringExtra("news_link");

        newsPos = getIntent().getIntExtra("news_position",0);

        complete = getIntent().getBooleanExtra("news_complete",false);

        findViewById();
        initView();
        initData();
    }

    public void initView() {
        if (adapter == null) {
            rv.setLayoutManager(new GridLayoutManager(this, 3));
            rv.setItemAnimator(new DefaultItemAnimator());
            adapter = new ImageRecyclerAdapter(this, images);
            adapter.setItemClickListener(new OnRecyclerItemClickListener() {
                @Override
                public void click(View item, int position) {
//                    Mango.setImages(images);
//                    Mango.setPosition(position);
//                    Mango.setImageSelectListener(new ImageSelectListener() {
//                        @Override
//                        public void select(int index) {
//                            Log.d("Mango", "select: " + index);
//                        }
//                    });
//                    try {
//                        Mango.open(((AppCompatActivity) getActivity()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            });
            rv.setAdapter(adapter);
        }
    }

    @Override
    public void setImages(List<MultiplexImage> images) {
        this.images = images;
    }

    @Override
    public void findViewById() {
        rv = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void initData() {
        DataUtil.sendOkHttpRequest(newsLink, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("errorOnNewsContent", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] responseByte = response.body().bytes();
                String responseData = new String(responseByte, "GB2312");
                new Thread(new sendValueToServer(responseData,newsLink) {
                    @Override
                    public void run() {
                        String html1;
                        html1 = this.html;
                        String htmllink;
                        htmllink = this.htmllink;

                        Bundle bundle = new Bundle();
                        bundle.putString("html", html1);
                        bundle.putString("htmllink",htmllink);
                        Message msg = handler1.obtainMessage();
                        msg.setData(bundle);
                        handler1.sendMessage(msg);
                    }
                }).start();
            }
        });
    }


    public void refresh(String newsTitle, String newsLink, int position,boolean complete) {

    }

    Handler handler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg){
            String html = null;
            Bundle bundle = msg.getData();
            int index = (int) bundle.get("index");
//            Log.i("index", String.valueOf(index));
            html = bundle.get("html").toString();

            if(TextUtils.isEmpty(html)){

                Document doc = Jsoup.parse(html);
                Elements image_doc = doc.getElementsByTag("img");
                String imgsrc = image_doc.get(0).attr("src");
                Elements title_doc = doc.getElementsByTag("meta");
                String title = title_doc.get(0).attr("content");

                Log.i("warmming","abc"+imgsrc);

                images.add(new MultiplexImage(imgsrc,imgsrc,MultiplexImage.ImageType.NORMAL));

                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });


    @SuppressLint("HandlerLeak")
    Handler handler1 = new Handler(new Handler.Callback() {
        int j;
        @Override
        public boolean handleMessage(Message msg){
            Bundle bundle = msg.getData();

            if(!TextUtils.isEmpty(bundle.get("html").toString())){
                String html = bundle.get("html").toString();
                Document doc = Jsoup.parse(html);
                Elements page = doc.getElementsByTag("li");
                int num = page.size()-6;
                if(num == -6){
                    Intent intent = new Intent(NewsContentActivity.this,EmptyActivity.class);
                    startActivity(intent);
                }else {
                    String htmllink = bundle.get("htmllink").toString();
                    String htmlhead = htmllink.substring(0,htmllink.length()-5);
                    String htmlfoot = htmllink.substring(htmllink.length()-5,htmllink.length());

                    for(j = 1 ; j <= num ; j ++){
                        String link;
                        if(j == 1)link = htmlhead + htmlfoot;
                        else link = htmlhead+'_'+j+htmlfoot;
                        Log.i("htmllink",link);
                        DataUtil.sendOkHttpRequest(link, new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("errorOnNewsContent", e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                byte[] responseByte = response.body().bytes();
                                String responseData = new String(responseByte, "GB2312");
                                new Thread(new sendValueToServer(responseData) {
                                    @Override
                                    public void run() {
                                        String html1;
                                        html1 = this.html;
                                        int index = this.html.charAt(this.html.length()-1);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("html",html1);
                                        bundle.putInt("index",index-'0');
                                        Message msg = handler2.obtainMessage();
                                        msg.setData(bundle);
                                        handler2.sendMessage(msg);
                                    }
                                }).start();
                            }
                        });
                    }
                }

            }

            return false;
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        images.clear();
    }
}