package com.zzz.newsapplication.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.zzz.newsapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
//    private static final int LOAD_SUCCESS = 1;
//    // 加载失败
//    private static final int LOAD_ERROR = -1;
//    ImageView imageview;
//    Bitmap bitmap1;
//    Bitmap bitmap2;


//    private GirdAdapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            bitmap1 = returnBitMap("https://avatar.csdn.net/C/6/8/1_bz419927089.jpg");
//            bitmap2 = returnBitMap("http://bmob-cdn-1178.b0.upaiyun.com/2016/05/10/1a11e2a135bf4bc29fd60831c64f558c.jpg");
//            handler.sendEmptyMessage(0);
//        }
//    };
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            super.handleMessage(msg);
////            System.out.println(baozhi_list.size());
////            for(int i = 0 ; i < baozhi_list.size() ; i ++){
////                System.out.println(baozhi_list.get(i).getTitle());
////            }
////            for(int i = 0 ; i < baozhi_list.size() ; i ++){
////                System.out.println(baozhi_list.get(i).getNewsLink());
////            }
////            adapter.
//            imageview.setImageBitmap(bitmap1);
//            imageview.setImageBitmap(bitmap2);
//        }
//    };
//    public Bitmap returnBitMap(String url) {
//        URL myFileUrl = null;
//        Bitmap bitmap = null;
//        try {
//            myFileUrl = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }

}



