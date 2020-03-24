package com.zev.wanandroid.mvp.model.api.service;

import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.LinkEntity;
import com.zev.wanandroid.mvp.model.entity.MyScoreEntity;
import com.zev.wanandroid.mvp.model.entity.MyShareEntity;
import com.zev.wanandroid.mvp.model.entity.ScoreEntity;
import com.zev.wanandroid.mvp.model.entity.UserEntity;
import com.zev.wanandroid.mvp.model.entity.UserinfoEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    // 注册
    @POST("user/register")
    Observable<BaseEntity<UserEntity>> registerUser(@Query("username") String name, @Query("password") String password, @Query("repassword") String repwd);

    // 登陆
    @POST("user/login")
    Observable<BaseEntity<UserEntity>> loginUser(@Query("username") String name, @Query("password") String password);

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<BaseEntity<UserinfoEntity>> getUserInfo();

    /**
     * 积分排行榜
     *
     * @param page
     * @return
     */
    @GET("coin/rank/{page}/json")
    Observable<BaseEntity<ScoreEntity>> getScoreRank(@Path("page") int page);

    /**
     * 个人积分
     *
     * @param page
     * @return
     */
    @GET("lg/coin/list/{page}/json")
    Observable<BaseEntity<MyScoreEntity>> getMyScore(@Path("page") int page);


    /**
     * 我的收藏文章列表
     *
     * @param page
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<BaseEntity<ChapterEntity>> getMyCollect(@Path("page") int page);

    /**
     * 获取我的收藏文章
     *
     * @return
     */
    @GET("lg/collect/usertools/json")
    Observable<BaseArrayEntity<LinkEntity>> getMyLink();

    /**
     * 删除收藏网址
     *
     * @param id
     * @return
     */
    @POST("lg/collect/deletetool/json")
    Observable<BaseEntity> deleteMyLink(@Query("id") int id);


    /**
     * 更新收藏网址
     *
     * @param id
     * @param name
     * @param link
     * @return
     */
    @POST("lg/collect/updatetool/json")
    Observable<BaseEntity> updateMyLink(@Query("id") int id, @Query("name") String name, @Query("link") String link);


    /**
     * 获取我的分享
     *
     * @param page
     * @return
     */
    @GET("user/lg/private_articles/{page}/json")
    Observable<BaseEntity<MyShareEntity>> getMyShard(@Path("page") int page);
}
