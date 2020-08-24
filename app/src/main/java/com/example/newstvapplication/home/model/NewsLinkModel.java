package com.example.newstvapplication.home.model;

/**
 * Created by oruret on 2018/1/31.
 */

public class NewsLinkModel {
    private String title;
    private String newsLink;
    private boolean isGlobal = false;

    public NewsLinkModel(String title, String newsLink) {
        this.title = title;
        this.newsLink = newsLink;
        this.isGlobal = title.contains("环球时报");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

}