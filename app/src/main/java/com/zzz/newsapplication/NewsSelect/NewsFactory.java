package com.zzz.newsapplication.NewsSelect;

import android.util.Log;

public class NewsFactory {
    public NewsFactory(){

    }
    public AbsNewsRepository getNewsRepository(int source){
        switch (source){
            case 1:
                Log.e("九维网","");
                return new NewsRepository(source);
            case 2:
                Log.e("海天网","");
                return new htNewsRepository(source);
            case 3:
                Log.e("角度网","");
                return new jdqNewsRepository(source);
        }
        return null;
    }
}
