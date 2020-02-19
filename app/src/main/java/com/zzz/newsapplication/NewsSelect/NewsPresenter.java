package com.zzz.newsapplication.NewsSelect;

import androidx.annotation.NonNull;

import android.widget.Toast;

import com.zzz.newsapplication.Bean.NewsLink;
import com.zzz.newsapplication.Utils.NewsInterface;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class NewsPresenter {
    private final AbsNewsRepository mNewsRepository;
    private final NewsFragment mNewsView;
    public NewsPresenter(@NonNull AbsNewsRepository newsRepository, @NonNull NewsFragment newsTitleFragment1){
        mNewsRepository = checkNotNull(newsRepository,"newsRepository cannot be null");
        mNewsView = checkNotNull(newsTitleFragment1,"newsView cannot be null");
        mNewsView.setPresenter(this);
    }

    private NewsInterface.NetworkCallback networkCallback = new NewsInterface.NetworkCallback(){
        @Override
        public void onGetSuccess(String resHtml) {
            final List<NewsLink> newsLinkList = mNewsRepository.parseNewsHtml(resHtml);
            mNewsView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    processNews(newsLinkList);
                    mNewsView.mSetRefreshing(false);
                }
            });
        }
        @Override
        public void onGetFail(Exception ex) {
            mNewsView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNewsView.showFailuePage();
                }
            });
            Toast.makeText(mNewsView.getContext(),"Can't get newslist"+ex.getMessage(),Toast.LENGTH_LONG);
        }
    };

    public void loadNews() {
        mNewsRepository.getNewsList(networkCallback);
    }
    public void processNews(List<NewsLink> newsLinkList){
        if(newsLinkList.isEmpty()){
            mNewsView.showFailuePage();
        }else{
            mNewsView.showNewsList(newsLinkList);
            mNewsView.hideFailuePage();
        }
    }
    public void openNewsDetail(NewsLink newsLink){
        checkNotNull(newsLink,"requestNews cannot be null");
        mNewsView.showNewsDetail(newsLink);
    }
}
