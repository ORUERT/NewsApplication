package com.zzz.newsapplication.NewsSelect;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.zzz.newsapplication.bean.NewsLink;
import com.zzz.newsapplication.Utils.NewsInterface;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class NewsPresenter {
    private final NewsRepository mNewsRepository;
    private final NewsFragment mNewsView;
    public NewsPresenter(@NonNull NewsRepository newsRepository,@NonNull NewsFragment newsTitleFragment1){
        mNewsRepository = checkNotNull(newsRepository,"newsRepository cannot be null");
        mNewsView = checkNotNull(newsTitleFragment1,"newsView cannot be null");

        mNewsView.setPresenter(this);
    }

    NewsInterface.NetworkCallback networkCallback = new NewsInterface.NetworkCallback(){
        @Override
        public void onGetSuccess(String resHtml) {
            final List<NewsLink> newsLinkList = mNewsRepository.parseNewsHtml(resHtml);
            mNewsView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    processNews(newsLinkList);
                }
            });

        }
        @Override
        public void onGetFail(Exception ex) {
            Toast.makeText(mNewsView.getContext(),"Can't get newslist",Toast.LENGTH_LONG);
        }
    };

    public void loadNews() {
        mNewsRepository.getNewsList(networkCallback);
    }
    public void processNews(List<NewsLink> newsLinkList){
        if(newsLinkList.isEmpty()){
        //
        }else{
            mNewsView.showNewsList(newsLinkList);
        }
    }
    public void openNewsDetail(NewsLink newsLink){
        checkNotNull(newsLink,"requestNews cannot be null");
        mNewsView.showNewsDetail(newsLink);
    }
}
