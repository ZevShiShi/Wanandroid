package com.zev.wanandroid.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class NavigationEntity implements Serializable {

    private int cid;
    private String name;
    private List<Chapter> articles;


    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public List<Chapter> getArticles() {
        return articles;
    }
}
