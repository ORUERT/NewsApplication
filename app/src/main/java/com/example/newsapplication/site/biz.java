//package com.example.newsapplication.site;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.example.newsapplication.bean.CommonException;
//import com.example.newsapplication.bean.News;
//import com.jelly.mango.MultiplexImage;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
////import
//
//
///**
// * Created by oruret on 2018/1/31.
// */
//
//public class biz {
//    private String firsthtml;
//    private String htmlLink;
//    private boolean flagstop = false;
//    private List<News> newsList_one = new ArrayList<News>();
//    private int j;
////    private int count = 0;
//    public List<News> getNewsItems(String htmlLink)throws CommonException{
//
//        this.htmlLink = htmlLink;
//        try {
////            firsthtml = DataUtil.doGet(htmlLink);
//            Document doc = Jsoup.parse(firsthtml);
//            Elements page = doc.getElementsByTag("li");
//            int num = page.size()/2-3;
//
////            Log.i("hello", temp.get(0).getElementsByTag("a")+String.valueOf(temp.size())+"asdfghj");
//
//            for(j = 1 ; j <= num ; j ++){
////                Log.i("html", String.valueOf(j)+" "+htmlLink+"&pageno="+j);
//                new Thread(runnable).start();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.i("hello", String.valueOf(newsList_one.size()));
//        return newsList_one;
//    }
//
//    public News news_one(String newslink , int num){
//
//        Document doc = Jsoup.parse(newslink);
//        Elements image_doc = doc.getElementsByTag("img");
//        String imgsrc = image_doc.get(0).attr("src");
//        Elements title_doc = doc.getElementsByTag("meta");
//        String title = title_doc.get(0).attr("content");
//        int id = num;
//        News news = new News();
//        news.setImgLink(imgsrc);
//        news.setId(id);
//        news.setTitle(title);
//        return news;
//    }
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            while(!flagstop){
//            String html = null;
//            try {
//                html = DataUtil.doGet(htmlLink+"&pageno="+j);
//            } catch (CommonException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Bundle bundle = new Bundle();
//            bundle.putString("html",html);
//            Message msg = handler.obtainMessage();
//            msg.setData(bundle);
//            handler.sendMessage(msg);
//            }
//        }
//    };
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            super.handleMessage(msg);
//            Bundle bundle = msg.getData();
//            String html = bundle.get("html").toString();
//            newsList_one.add(news_one(html , j ));
//            flagstop = true;
////            count++;
//        }
//    };
//}
