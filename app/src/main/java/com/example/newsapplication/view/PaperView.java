package com.zzz.newsapplication.view;

import com.zzz.newsapplication.bean.NewsLink;
import com.jelly.mango.MultiplexImage;

import java.util.List;

/**
 * Created by zhouy on 2018/2/7.
 */

public interface PaperView {
    void setPaper(List<NewsLink> paper);


    void initRecycler();
}
