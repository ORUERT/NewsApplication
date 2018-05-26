package com.zzz.newsapplication.view;

import com.jelly.mango.MultiplexImage;

import java.util.List;

/**
 * Created by zhouy on 2018/2/16.
 */

public interface ImageView {
    void setImages(List<MultiplexImage> images);

    void findViewById();
    void initData();
    void initView();
}
