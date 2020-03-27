package com.zev.wanandroid.mvp.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HotSearchBean implements Serializable {
    public String title;
    public List<String> names;
    public boolean showClear;

    public HotSearchBean(String title, boolean showClear) {
        this.title = title;
        this.showClear = showClear;
        names = new ArrayList<>();
    }

    public HotSearchBean() {
    }
}
