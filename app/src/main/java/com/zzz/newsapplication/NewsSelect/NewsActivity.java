package com.zzz.newsapplication.NewsSelect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzz.newsapplication.R;
import com.zzz.newsapplication.Utils.ActivityUtils;

public class NewsActivity extends AppCompatActivity {
    NewsPresenter mNewsPresenter;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.newslist_act);

        NewsFragment newsTitleFragment1 = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (newsTitleFragment1 == null) {
            // Create the fragment
            newsTitleFragment1 = NewsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), newsTitleFragment1, R.id.contentFrame);
        }
        mNewsPresenter = new NewsPresenter(new NewsRepository(),newsTitleFragment1);
    }
}
