package com.zzz.newsapplication.NewsSelect;

import androidx.annotation.NonNull;

import com.zzz.newsapplication.Bean.NewsLink;
import com.zzz.newsapplication.Utils.DataUtil;
import com.zzz.newsapplication.Utils.NewsInterface;

import java.util.List;


public abstract class AbsNewsRepository {
    int mNewsource = 0;
    String mBaseUrl = "http://www.hqck.net/";
    public void getNewsList(@NonNull NewsInterface.NetworkCallback networkCallback){
        switch (mNewsource){
            case 1:
                mBaseUrl ="http://www.hqck.net/";break;
            case 2:
                mBaseUrl ="http://www.ht5.com/";break;
            case 3:
                mBaseUrl ="http://www.jdqu.com/";break;
        }
        DataUtil.sendOkHttpRequestSyn(mBaseUrl,networkCallback);
    }
    public void setSource(int source){
        mNewsource = source;
    }
    public abstract List<NewsLink> parseNewsHtml(@NonNull String newsHtml);
}
