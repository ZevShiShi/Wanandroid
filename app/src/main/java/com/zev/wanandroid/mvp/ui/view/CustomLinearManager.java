package com.zev.wanandroid.mvp.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * 禁用垂直滚动
 */
public class CustomLinearManager extends LinearLayoutManager {

    public CustomLinearManager(Context context) {
        super(context);
    }


    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
