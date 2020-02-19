package com.zzz.newsapplication.NewsSelect;

import androidx.annotation.NonNull;

import android.util.Log;

import com.zzz.newsapplication.Bean.NewsLink;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class htNewsRepository extends AbsNewsRepository{
    public htNewsRepository(int source){
        mNewsource = source;
    }
    @Override
    public List<NewsLink> parseNewsHtml(@NonNull String newsHtml) {
        Log.e("pageurl",mBaseUrl);
        List<NewsLink> mNewsList = new ArrayList<>();
        Document doc = Jsoup.parse(newsHtml);
        Element mElement = doc.getElementById("mil_zhong001_left2_list");
        Log.e("hellow",mElement.toString());
        Elements mElements = mElement.getElementsByTag("a");
        for(int i = 0 ; i < mElements.size() ; i ++){
            NewsLink newsLink = new NewsLink();
            String pagelink = mBaseUrl+mElements.get(i).attr("href");//?
            Elements temp = mElements.get(i).getElementsByTag("font");

            if(temp.isEmpty()){
                newsLink.setColorisblack(true);
                newsLink.setTitle(mElements.get(i).text());
            }else{
                newsLink.setColorisblack(false);
                newsLink.setTitle(temp.text());
            }
            newsLink.setNewsLink(pagelink);
            mNewsList.add(newsLink);

        }
        return mNewsList;
    }
}
