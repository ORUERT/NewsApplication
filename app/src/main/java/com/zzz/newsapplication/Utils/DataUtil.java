package com.zzz.newsapplication.Utils;

import androidx.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by oruret on 2018/1/31.
 */


public class DataUtil {



    public static void sendOkHttpRequestSyn(@NonNull String address, @NonNull final NewsInterface.NetworkCallback networkCallback){
        OkHttpClient client =  new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                networkCallback.onGetFail(e);
                Log.i("errorOnNewsContent", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] responseByte = response.body().bytes();
                String responseData = new String(responseByte, "GB2312");
                networkCallback.onGetSuccess(responseData);

            }
        });
    }
    public static void sendOkHttpRequestSyn(@NonNull String address, @NonNull final int position, @NonNull final NewsInterface.NetworkWithPosCallback networkCallback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("errorOnNewsContent", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] responseByte = response.body().bytes();
                String responseData = new String(responseByte, "GB2312");
                networkCallback.onGetSuccess(position,responseData);

            }
        });
    }
//    public static void sendOkHttpRequestAsy(String address, final NewsInterface.GetNewsHtmlCallback callback) {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(address)
//                .build();
//        final Call call = client.newCall(request);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = call.execute();
//                    byte [] responseByte = response.body().bytes();
//                    String responseHtml = new String(responseByte,"GB2312");
//                    callback.onNewsLoaded(responseHtml);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}
