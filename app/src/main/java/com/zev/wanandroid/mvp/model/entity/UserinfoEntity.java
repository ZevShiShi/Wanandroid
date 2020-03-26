package com.zev.wanandroid.mvp.model.entity;

import java.io.Serializable;

public class UserinfoEntity implements Serializable {

    private int coinCount;
    private int level;
    private int rank;
    private int userId;
    private String username;


    public int getCoinCount() {
        return coinCount;
    }

    public int getLevel() {
        return level;
    }

    public int getRank() {
        return rank;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
