package com.zev.wanandroid.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class ChapterEntity implements Serializable {

    private int curPage;
    private List<Chapter> datas;
    private boolean over;
    private int size;
    private int total;


    public int getCurPage() {
        return curPage;
    }

    public List<Chapter> getDatas() {
        return datas;
    }

    public boolean isOver() {
        return over;
    }

    public int getSize() {
        return size;
    }

    public int getTotal() {
        return total;
    }
}
