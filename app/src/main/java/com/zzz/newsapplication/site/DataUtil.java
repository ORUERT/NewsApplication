package com.zzz.newsapplication.site;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zzz.newsapplication.bean.CommonException;
import com.jelly.mango.MultiplexImage;

import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by oruret on 2018/1/31.
 */

public class DataUtil {
    String html;
    String urlStr;

//    public static String doGet(final String urlStr) throws CommonException, IOException {
//        final StringBuffer sb = new StringBuffer();
//        URL url = new URL(urlStr);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setConnectTimeout(20000);
//        conn.setDoOutput(true);
//        String temp = String.valueOf(conn.getResponseCode());
////        Log.i("hello", temp);
//        if (conn.getResponseCode() == 200) {
//            InputStream feed = conn.getInputStream();
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while ((len = feed.read(buf)) != -1) {
//                sb.append(new String(buf, 0, len, "GB2312"));
//            }
//        } else {
//            if (conn.getResponseCode() == 404) {
//
//            }
//        }
//
//
//
//
//
//        return sb.toString();
//    }
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
