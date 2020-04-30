package com.zzz.newsapplication.NewsImageSelect;

import androidx.annotation.NonNull;

import com.jelly.mango.MultiplexImage;
import com.zzz.newsapplication.Utils.NewsInterface;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewsImgPresenter {
    private final NewsImgRepository mNewsImgRepository;
    private final NewsImageFragment mNewsImgView;
    public NewsImgPresenter(@NonNull NewsImgRepository newsImgRepository, @NonNull NewsImageFragment newsImageFragment){
        mNewsImgRepository = checkNotNull(newsImgRepository,"newsRepository cannot be null");
        mNewsImgView = checkNotNull(newsImageFragment,"newsView cannot be null");
        mNewsImgView.setPresenter(this);
    }

    private List<MultiplexImage> imageList = new ArrayList<>();



    public void loadImages(NewsInterface.NetworkWithPosCallback networkCallback) {
        mNewsImgRepository.getImageList(networkCallback);
    }

    public void replaceImage(ArrayList<String> imageList){
        if(imageList.isEmpty()){
            //
        }else{
            mNewsImgView.showImageList(imageList);
        }
    }
}
