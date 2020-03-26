package com.zev.wanandroid.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class SetupEntity implements Serializable {

    private List<SecondSetup> children;
    private int id;
    private int courseId;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;


    public List<SecondSetup> getChildren() {
        return children;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }


}
