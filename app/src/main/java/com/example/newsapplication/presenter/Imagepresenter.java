//package com.example.newsapplication.presenter;
//
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.example.newsapplication.bean.CommonException;
//import com.example.newsapplication.bean.News;
//import com.example.newsapplication.view.ImageView;
//import com.example.newsapplication.view.PaperView;
////import com.example.newsapplication.site.biz.getNewsItems;
//import com.jelly.mango.MultiplexImage;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import static com.example.newsapplication.site.URLUtil.generateUrl;
//
///**
// * Created by zhouy on 2018/2/7.
// */
//
//public class Imagepresenter {
//    private ImageView view;
//    private List<MultiplexImage> images;
//    private static List<News> news;
//    private String newsLink;
//    private int newsPos;
//    private boolean flagstop = false;
//
//    public Imagepresenter(ImageView view) {
//        this.view = view;
//    }
//
//    public void loadImage(String newsTitle , String newsLink , int newsPos) {
//        if (images == null) images = new ArrayList<MultiplexImage>();
//        this.newsLink = newsLink;
//        this.newsPos = newsPos;
//        biz tmp = new biz();
//        try {
//            news = tmp.getNewsItems(newsLink);
//        } catch (CommonException e) {
//            e.printStackTrace();
//        }
//        images.clear();
//            for(int i = 0 ; i < news.size(); i ++){
////                Log.i("hello",news.get(i).getTitle());
//                images.add(new MultiplexImage(news.get(i).getImgLink(),news.get(i).getImgLink(),MultiplexImage.ImageType.NORMAL));
//            }
//            view.setImages(images);
//            view.initRecycler();
////        new Thread(runnable).start();
//    }
//
////    Runnable runnable = new Runnable() {
////        @Override
////        public void run() {
//////            while(!flagstop){
////                biz tmp = new biz();
////                try {
////                    Log.i("hello","asdasdasdasd");
////                    news = tmp.getNewsItems(newsLink);
////                    Log.i("hello","asdasdasdasd");
////                } catch (CommonException e) {
////                    e.printStackTrace();
////                }
////                handler.sendEmptyMessage(0);
//////            }
////        }
////    };
////    Handler handler = new Handler(){
////        @Override
////        public void handleMessage(Message msg){
////            super.handleMessage(msg);
////            for(int i = 0 ; i < news.size() ;i  ++){
////                Log.i("hello",news.get(i).getTitle()+" "+news.get(i).getImgLink());
////            }
////            Log.i("hello" , "helloworld");
////            images.clear();
////            for(int i = 0 ; i < news.size(); i ++){
//////                Log.i("hello",news.get(i).getTitle());
////                images.add(new MultiplexImage(news.get(i).getImgLink(),news.get(i).getImgLink(),MultiplexImage.ImageType.NORMAL));
////            }
////            view.setImages(images);
////            view.initRecycler();
////
////            flagstop = true;
////
////        }
////    };
//
//}
