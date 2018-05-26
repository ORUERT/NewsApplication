//package com.example.newsapplication.site;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.example.newsapplication.bean.CommonException;
//import com.example.newsapplication.bean.NewsLink;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by oruret on 2018/1/31.
// */
//
//public class URLUtil {
//    public static final String NEWS_BASE_URL = "http://www.ckxx123.com";
//    private String html;
//    private boolean flagstop = false;
//    public List<NewsLink> generateUrl(){
//        List<NewsLink> newsList_many = new ArrayList<NewsLink>();
//        new Thread(runnable).start();
//
////             = temp.getHtml();
//        Log.i("hello",html);
////            System.out.println(htmlStr);
//        Document doc = Jsoup.parse(html);
//        Elements baozhi_list = doc.getElementsByClass("item-baozhi");
//        Log.i("baozhi_list.size()", String.valueOf(baozhi_list));
//        for(int i = 0 ; i < baozhi_list.size() ; i ++){
//            NewsLink newslink = new NewsLink();
//            String pagelink = NEWS_BASE_URL+baozhi_list.get(i).attr("href");//?
//
//            String pagetitle = baozhi_list.get(i).getElementsByTag("span").text();
//            Log.i("hello",pagelink+"  "+pagetitle);
//            newslink.setNewsLink(pagelink);
//            newslink.setTitle(pagetitle);
//            newsList_many.add(newslink);
//        }
//
//        return newsList_many;
//    }
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            while(!flagstop){
//                String html1 = null;
//                try {
//                    html1 = DataUtil.doGet(NEWS_BASE_URL);
//                } catch (CommonException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Bundle bundle = new Bundle();
//                bundle.putString("html",html1);
//                Message msg = handler.obtainMessage();
//                msg.setData(bundle);
//                handler.sendMessage(msg);
//            }
//        }
//    };
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            super.handleMessage(msg);
//            Bundle bundle = msg.getData();
//            html = bundle.get("html").toString();
//            flagstop = true;
////            count++;
//        }
//    };
//}
