package com.zzz.newsapplication.NewsSelect;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zzz.newsapplication.Utils.DataUtil;
import com.zzz.newsapplication.Bean.NewsLink;
import com.zzz.newsapplication.Utils.NewsInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.zzz.newsapplication.NewsSelect.NewsFragment.NEWS_BASE_URL;

public class NewsRepository {

    public NewsRepository(){

    }

    public void getNewsList(@NonNull NewsInterface.NetworkCallback networkCallback){
        DataUtil.sendOkHttpRequestSyn(NEWS_BASE_URL,networkCallback);
    }

    public List<NewsLink> parseNewsHtml(@NonNull String newsHtml){
        Log.e("isNull",newsHtml);
        List<NewsLink> mNewsList = new ArrayList<>();
        Document doc = Jsoup.parse(newsHtml);
        //获取左侧报纸日期元素信息
        Elements html_list = doc.getElementsByClass("jBoxBody");
        html_list = html_list.get(0).getElementsByTag("li");
        //Log.e("sss",html_list.toString());
        for(int i = 0 ; i < html_list.size() ; i ++){
            NewsLink newslink = new NewsLink();
            //Log.e("suffix",html_list.get(i).toString());
            //获取链接、颜色、当日标题
            String pagelink = NEWS_BASE_URL+html_list.get(i).getElementsByTag("a").attr("href");//?
            String pagecolor = html_list.get(i).getElementsByTag("a").attr("class");
            String pagetitle = html_list.get(i).getElementsByTag("a").text();

            Log.e("hello",pagelink+"  "+pagetitle);

            newslink.setNewsLink(pagelink);
            newslink.setTitle(pagetitle);

            if(pagecolor.contains("Red")){
                newslink.setColorisblack(false);
            }else if(pagecolor.contains("Gray")){
                newslink.setColorisblack(true);
            }
            mNewsList.add(newslink);
        }
        return mNewsList;
    }

}
