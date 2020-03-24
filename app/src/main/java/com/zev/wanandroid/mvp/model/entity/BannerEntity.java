package com.zev.wanandroid.mvp.model.entity;

public class BannerEntity {

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;


    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int isVisible() {
        return isVisible;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "BannerEntity{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", isVisible=" + isVisible +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }




}
