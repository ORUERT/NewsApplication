package com.zzz.newsapplication.Utils;

public class LogUtil {
    public static String cutString(String s){
        if(s.length()>=23){
            s = s.substring(0,23);
        }
        return s;
    }
}
