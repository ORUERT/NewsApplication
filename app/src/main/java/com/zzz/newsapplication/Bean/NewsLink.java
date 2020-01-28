package com.zzz.newsapplication.Bean;

/**
 * Created by oruret on 2018/1/31.
 */

public class NewsLink {
    private String title;
    private String newsLink;
    private boolean colorisblack;

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getNewsLink(){
        return newsLink;
    }

    public void setNewsLink(String newsLink){
        this.newsLink = newsLink;
    }

    public boolean getColorisblack(){
        return colorisblack;
    }
    public void setColorisblack(boolean colorisblack){
        this.colorisblack = colorisblack;
    }
}
