package com.zev.wanandroid.mvp.model.entity.base;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.List;

public class BaseArrayEntity<T> {
    private int errorCode;
    private String errorMsg;
    private List<T> data;


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<T> getData() {
        return data;
    }

    public boolean isSuccess() {
        if (errorCode == 0 && ObjectUtils.isNotEmpty(getData())) {
            return true;
        }
        return false;
    }
}
