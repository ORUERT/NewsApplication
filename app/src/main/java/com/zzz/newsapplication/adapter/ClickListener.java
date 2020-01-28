package com.zzz.newsapplication.adapter;

import com.zzz.newsapplication.bean.NewsLink;

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
