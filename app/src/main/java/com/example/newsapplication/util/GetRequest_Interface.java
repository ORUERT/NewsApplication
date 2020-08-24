package com.example.newsapplication.util;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface GetRequest_Interface {
    @GET
    Observable<String> getHtmlByNetAsy(@Url String url);
    // @GET注解的作用:采用Get方法发送网络请求
    // getCall() = 接收网络请求数据的方法
    // 其中返回类型为Call<*>，*是接收数据的类（即上面定义的Translation类）
    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
    @GET
    Call<String> getHtmlByNetSyn(@Url String url);

    @GET
    @Streaming
    Observable<ResponseBody> downloadPicFromNet(@Url String imageUrl);
}
