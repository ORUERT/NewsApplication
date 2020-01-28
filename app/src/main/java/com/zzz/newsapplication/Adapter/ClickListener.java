package com.zzz.newsapplication.Adapter;

import com.zzz.newsapplication.Bean.NewsLink;

/**
 * Created by Jelly on 2016/9/3.
 */
public interface ClickListener{
    interface OnRecyclerItemClickListener {

        void click(NewsLink item);
    }
    interface OnImageItemClickListener {

        void click(int position);
    }
}
