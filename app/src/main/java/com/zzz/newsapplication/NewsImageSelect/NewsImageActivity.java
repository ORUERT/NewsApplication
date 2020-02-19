package com.zzz.newsapplication.NewsImageSelect;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.zzz.newsapplication.R;
import com.zzz.newsapplication.Adapter.ImageListAdapter;
import com.zzz.newsapplication.Utils.ActivityUtils;


public class NewsImageActivity extends AppCompatActivity{

    private RecyclerView rv;

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



}

