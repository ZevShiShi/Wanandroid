package com.zev.wanandroid.mvp.model.entity;

import java.io.Serializable;

public class HotSearchEntity implements Serializable {

    private int id;
    private String link;
    private String name;
    private int order;
    private int visible;


    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getVisible() {
        return visible;
    }
}
