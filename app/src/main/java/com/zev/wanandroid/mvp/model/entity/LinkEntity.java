package com.zev.wanandroid.mvp.model.entity;

import java.io.Serializable;

public class LinkEntity implements Serializable {

    private int id;
    private String icon;
    private String desc;
    private String link;
    private String name;
    private int order;
    private int userId;
    private int visible;


    public int getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getDesc() {
        return desc;
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

    public int getUserId() {
        return userId;
    }

    public int getVisible() {
        return visible;
    }
}
