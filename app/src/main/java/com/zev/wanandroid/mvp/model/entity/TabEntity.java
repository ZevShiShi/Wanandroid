package com.zev.wanandroid.mvp.model.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {

    private String title;
    private int iconSelected;
    private int iconUnselected;

    public TabEntity(String title, int iconSelected, int iconUnselected) {
        this.title = title;
        this.iconSelected = iconSelected;
        this.iconUnselected = iconUnselected;
    }


    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return iconSelected;
    }

    @Override
    public int getTabUnselectedIcon() {
        return iconUnselected;
    }
}
