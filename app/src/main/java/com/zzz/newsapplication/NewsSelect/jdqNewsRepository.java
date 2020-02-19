package com.zzz.newsapplication.NewsSelect;

import androidx.annotation.NonNull;
import android.util.Log;

import com.zzz.newsapplication.Bean.NewsLink;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class jdqNewsRepository extends AbsNewsRepository{
    public jdqNewsRepository(int source){
        mNewsource = source;
    }
    @Override
    public List<NewsLink> parseNewsHtml(@NonNull String newsHtml) {
        List<NewsLink> mNewsList = new ArrayList<>();
        Document doc = Jsoup.parse(newsHtml);
        Elements mElement = doc.getElementsByClass("zxgx-nr");
//        Log.e("asd", String.valueOf(mElement.size()));
        mElement = mElement.get(0).getElementsByTag("a");
        Log.e("asd", String.valueOf(mElement.size()));
        for(int i = 0 ; i < mElement.size() ; i ++){
            NewsLink newsLink = new NewsLink();
            String pagelink = mBaseUrl+mElement.get(i).attr("href");//?
//            String pagecolor = html_list.get(i).getElementsByTag("a").attr("class");
            String pagetitle = mElement.get(i).text();
            Log.e(pagetitle,pagelink);
            newsLink.setNewsLink(pagelink);
            newsLink.setTitle(pagetitle);
            mNewsList.add(newsLink);
        }
        return mNewsList;
    }
}
