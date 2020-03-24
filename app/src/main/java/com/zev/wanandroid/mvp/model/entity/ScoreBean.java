package com.zev.wanandroid.mvp.model.entity;

public class ScoreBean {

    private int rank;
    private int userId;
    private int level;
    private int coinCount;
    private int high;
    private String username;


    private boolean showGold;
    private boolean showSliver;
    private boolean showCopper;


    public ScoreBean(int rank, int userId, int level, int coinCount, String username) {
        this.rank = rank;
        this.userId = userId;
        this.level = level;
        this.coinCount = coinCount;
        this.username = username;
    }


    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isShowGold() {
        return showGold;
    }

    public void setShowGold(boolean showGold) {
        this.showGold = showGold;
    }

    public boolean isShowSliver() {
        return showSliver;
    }

    public void setShowSliver(boolean showSliver) {
        this.showSliver = showSliver;
    }

    public boolean isShowCopper() {
        return showCopper;
    }

    public void setShowCopper(boolean showCopper) {
        this.showCopper = showCopper;
    }
}
