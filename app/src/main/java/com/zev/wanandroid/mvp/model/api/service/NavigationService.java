package com.zev.wanandroid.mvp.model.api.service;

import com.zev.wanandroid.mvp.model.entity.NavigationEntity;
import com.zev.wanandroid.mvp.model.entity.SetupEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NavigationService {


    /**
     * 体系数据
     *
     * @return
     */
    @GET("tree/json")
    Observable<BaseArrayEntity<SetupEntity>> getSetup();


    /**
     * 导航数据
     *
     * @return
     */
    @GET("navi/json")
    Observable<BaseArrayEntity<NavigationEntity>> getNavigation();

}
