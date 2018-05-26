package com.zzz.newsapplication.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzz.newsapplication.R;
import com.zzz.newsapplication.adapter.NewsListAdapter;
import com.zzz.newsapplication.adapter.OnRecyclerItemClickListener;
import com.zzz.newsapplication.bean.NewsLink;
//import com.example.newsapplication.presenter.Paperpresenter;
import com.zzz.newsapplication.site.DataUtil;
//import com.example.newsapplication.adapter.

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

//import static com.example.newsapplication.site.URLUtil.generateUrl;

/**
 * Created by oruret on 2018/1/30.
 */

public class NewsTitleFragment extends Fragment implements PaperView{

    private boolean isTwoPane;
    public static final String NEWS_BASE_URL = "http://www.hqck.net/";
    private List<NewsLink> baozhi_list = new ArrayList<NewsLink>();
    private NewsListAdapter adapter;
    private RecyclerView ry;
    private View view;
    private boolean flagstop = false;
//    private Paperpresenter pp;
    private String html;
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle saveInstanceState){

        view = inflater.inflate(R.layout.news_title_frag,container,false);
        findViewById();
        initView();

        if(baozhi_list.size() == 0)initData();

        return view;
    }


    @Override
    public void setPaper(List<NewsLink> paper){
        this.baozhi_list = paper;
    }

    @Override
    public void findViewById() {
        ry = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
    }

    @Override
    public void initData() {
        DataUtil.sendOkHttpRequest(NEWS_BASE_URL,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("error",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte [] responseByte = response.body().bytes();
                String responseData = new String(responseByte,"GB2312");
                new Thread(new sendValueToServer(responseData){
                    @Override
                    public void run() {
                        String html1;
                        html1 = this.html;
                        Bundle bundle = new Bundle();
                        bundle.putString("html", html1);
                        Message msg = handler.obtainMessage();
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });
    }

    public void initView(){
        if(adapter == null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            ry.setLayoutManager(layoutManager);

            adapter = new NewsListAdapter(this.baozhi_list);

            adapter.setItemClickListener(new OnRecyclerItemClickListener() {
                @Override
                public void click(View item, int position) {

                    NewsLink news = baozhi_list.get(position);
                    if(isTwoPane){

                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getNewsLink(),position,news.getColorisblack());

                    }else{
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getNewsLink(),position,news.getColorisblack());
                    }
                }
            });
            ry.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTwoPane = getActivity().findViewById(R.id.news_content_layout) != null;
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg){
            Bundle bundle = msg.getData();
            html = bundle.get("html").toString();

            Document doc = Jsoup.parse(html);
            Elements html_list = doc.getElementsByClass("item-baozhi");

            for(int i = 0 ; i < html_list.size() ; i ++){
                NewsLink newslink = new NewsLink();
                String pagelink = NEWS_BASE_URL+html_list.get(i).attr("href");//?
                String pagecolor = html_list.get(i).getElementsByTag("span").attr("class");
                String pagetitle = html_list.get(i).getElementsByTag("span").text();

                Log.i("hello",pagelink+"  "+pagetitle);

                newslink.setNewsLink(pagelink);
                newslink.setTitle(pagetitle);
                if(pagecolor.contains("Red")){
                    newslink.setColorisblack(false);
                }else if(pagecolor.contains("Gray")){
                    newslink.setColorisblack(true);
                }
                baozhi_list.add(newslink);
                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });


}
