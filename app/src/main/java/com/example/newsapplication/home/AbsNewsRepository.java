package com.example.newsapplication.home;


public abstract class AbsNewsRepository {
    public static String mBaseUrl = "http://www.ht5.com/";
    public static String mSiteName = "海天网";
    public static String getmBaseUrl(){
        return mBaseUrl;
    }
    public static String getmSiteName() {return mSiteName;}
    public static int getmSource(){return mSource;}
    public static int mSource = 1;
    public static void setSource(int source){
        mSource = source;
        switch (source){
            case 1:
                mBaseUrl ="http://www.ht5.com/";
                mSiteName = "海天网";break;
            case 2:
                mBaseUrl ="http://www.hqck.net/";
                mSiteName = "九尾网";break;
            case 3:
                mBaseUrl ="http://www.jdqu.com/";
                mSiteName = "角度区";break;
        }
    }
}
