//package com.example.myapplication;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.annotation.Nullable;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.Target;
//import com.maning.imagebrowserlibrary.ImageEngine;
//
//public class GlideImageEngine implements ImageEngine {
//    @Override
//    public void loadImage(Context context, String url, ImageView imageView, final View progressView, View customImageView) {
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.card_image_movie_01)
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .error(R.mipmap.ic_launcher);
//        Glide.with(context)
//                .asBitmap()
////                .placeholder(R.drawable.placeholder)
//                .load(url)
//                .apply(options)
//                .listener(new RequestListener<Bitmap>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                        //隐藏进度View,必须设置setCustomProgressViewLayoutID
//                        progressView.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                        //隐藏进度View,必须设置setCustomProgressViewLayoutID
//                        progressView.setVisibility(View.GONE);
//                        return false;
//                    }
//                })
//                .into(imageView);
//    }
//}
