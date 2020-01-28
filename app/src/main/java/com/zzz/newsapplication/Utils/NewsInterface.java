package com.zzz.newsapplication.Utils;

public interface NewsInterface {

     interface NetworkCallback {
        void onGetSuccess(String resHtml);
        void onGetFail(Exception ex);
    }
    interface NetworkWithPosCallback {
        void onGetSuccess(int position,String resHtml);
        void onGetFail(Exception ex);
    }
}
