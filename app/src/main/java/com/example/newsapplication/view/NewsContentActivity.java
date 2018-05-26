package com.zzz.newsapplication.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zzz.newsapplication.R;

import static com.jelly.mango.Mango.position;

public class NewsContentActivity extends AppCompatActivity {
    public static void actionStart(Context context , String newsTitle , String newsLink,int newPos){
        Intent intent = new Intent(context , NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_link",newsLink);

        intent.putExtra("news_position",newPos);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        String newsTitle = getIntent().getStringExtra("news_title");

        String newsLink = getIntent().getStringExtra("news_link");


        int newsPos = getIntent().getIntExtra("news_position",0);

        NewsContentFragment newsContentFragment = (NewsContentFragment)getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle , newsLink, newsPos);
    }
}
