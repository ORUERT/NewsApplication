//package com.example.newsapplication.presenter;
//
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.example.newsapplication.bean.NewsLink;
////import com.example.newsapplication.site.URLUtil;
//import com.example.newsapplication.view.PaperView;
////import com.example.newsapplication.site.URLUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import static com.example.newsapplication.site.URLUtil.generateUrl;
//
//
///**
// * Created by zhouy on 2018/2/7.
// */
//
//public class Paperpresenter {
//
//    private PaperView view;
//    private static List<NewsLink> paper;
//    private boolean flagstop = false;
//
//    public Paperpresenter(PaperView view){
//        this.view = view;
//    }
//
//    public void loadPaper(){
//        if(paper == null) paper = new ArrayList<NewsLink>();
////        URLUtil url = new URLUtil();
//
////        paper = url.generateUrl();
////        view.setPaper(paper);
////        view.initRecycler();
////        new Thread(runnable).start();
//}
//
////        Runnable runnable = new Runnable() {
////        @Override
////        public void run() {
//////            while(!flagstop){
////                paper = generateUrl();
////                handler.sendEmptyMessage(0);
//////            }
////        }
////    };
////    Handler handler = new Handler(){
////        @Override
////        public void handleMessage(Message msg){
////            super.handleMessage(msg);
////            for(int i = 0 ; i < paper.size() ; i ++){
////                Log.i("hello",paper.get(i).getTitle()+"asd");
////            }
////            view.setPaper(paper);
////            view.initRecycler();
//////            flagstop = true;
////        }
////    };
//
//}
