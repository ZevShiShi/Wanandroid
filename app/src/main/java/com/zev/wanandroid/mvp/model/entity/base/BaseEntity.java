package com.zev.wanandroid.mvp.model.entity.base;

import com.blankj.utilcode.util.ObjectUtils;

public class BaseEntity<T> {

    private int errorCode;
    private String errorMsg;
    private T data;


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        if (errorCode == 0 && ObjectUtils.isNotEmpty(getData())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
