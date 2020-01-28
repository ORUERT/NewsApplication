package com.zzz.newsapplication.NewsImageSelect;

import android.support.annotation.NonNull;

import com.zzz.newsapplication.Utils.DataUtil;
import com.zzz.newsapplication.Utils.NewsInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewsImgRepository {
    String mBaseUrl;
    NewsInterface.NetworkWithPosCallback mImgCallback;
    public NewsImgRepository(String baseUrl){
        mBaseUrl = baseUrl;
    }
    public void getImageList(NewsInterface.NetworkWithPosCallback imgCallback){
        mImgCallback = imgCallback;
        getNewsDetailHtml();
    }

    public void getImageUrlList(List<String>newsDetailUrlList,NewsInterface.NetworkWithPosCallback imgCallback){
        for(int i = 0; i < newsDetailUrlList.size() ; i ++){
            DataUtil.sendOkHttpRequestSyn(newsDetailUrlList.get(i),i,imgCallback);
        }
    }

    private NewsInterface.NetworkCallback networkCallback = new NewsInterface.NetworkCallback(){
        @Override
        public void onGetSuccess(String resHtml) {
            List<String> newsDetailUrlList = getNewsDetailUrlList(resHtml);
            checkNotNull(newsDetailUrlList,"getNewsDetailUrlList error");
            getImageUrlList(newsDetailUrlList,mImgCallback);
        }

        @Override
        public void onGetFail(Exception ex) {

        }
    };

    public void getNewsDetailHtml(){
        DataUtil.sendOkHttpRequestSyn(mBaseUrl,networkCallback);
    }

    public List<String> getNewsDetailUrlList( @NonNull String resHtml){
        List<String> newsDetailList = new ArrayList<>();
        Document doc = Jsoup.parse(resHtml);
        Elements page = doc.getElementsByTag("option");
        //当期报纸页数
        int num = page.size()/2;
        //拼接报纸页面连接
        String htmllink = mBaseUrl;
        String htmlhead =   htmllink.substring(0,htmllink.lastIndexOf("/")+1);
        //   String htmlfoot = htmllink.substring(htmllink.length()-5,htmllink.length());

        for(int j = 1 ; j <= num ; j ++){
            String link = htmlhead+page.get(j-1).attr("value");
            newsDetailList.add(link);
        }
        return newsDetailList;
    }
}
