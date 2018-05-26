package com.zzz.newsapplication.bean;

/**
 * Created by oruret on 2018/1/30.
 */

public class News {
    private String title;
    private String imgLink;
    private int id;
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getImgLink(){
        return imgLink;
    }
    public void setImgLink(String imgLink){
        this.imgLink = imgLink;
    }
    public int getId(){return id;}
    public void setId(int id){this.id = id;}
}
