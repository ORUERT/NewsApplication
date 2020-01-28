package com.zzz.newsapplication.NewsImageSelect;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jelly.mango.MultiplexImage;
import com.zzz.newsapplication.R;
import com.zzz.newsapplication.adapter.ImageListAdapter;
import com.zzz.newsapplication.Utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;


public class NewsImageActivity extends AppCompatActivity{

    private RecyclerView rv;

    private List<MultiplexImage> images =new ArrayList<MultiplexImage>();
    private ImageListAdapter adapter;

    private String newsLink;
    private String newsTitle;
    private boolean complete;
    private int newsPos;

    private NewsImgPresenter mNewsPresenter;

    public static void actionStart(Context context , String newsTitle , String newsLink,boolean complete){
        Intent intent = new Intent(context , NewsImageActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_link",newsLink);
//        intent.putExtra("news_position",newPos);
        intent.putExtra("news_complete",complete);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_act);
        newsTitle = getIntent().getStringExtra("news_title");
        newsLink = getIntent().getStringExtra("news_link");
        newsPos = getIntent().getIntExtra("news_position",0);
        complete = getIntent().getBooleanExtra("news_complete",false);

        NewsImageFragment newsImageFragment = (NewsImageFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (newsImageFragment == null) {
            // Create the fragment
            newsImageFragment = newsImageFragment.newsInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), newsImageFragment, R.id.contentFrame);
        }
        mNewsPresenter = new NewsImgPresenter(new NewsImgRepository(newsLink), newsImageFragment);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        images.clear();
    }
}

