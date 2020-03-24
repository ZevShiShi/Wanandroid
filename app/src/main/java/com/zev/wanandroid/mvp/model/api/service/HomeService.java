package com.zev.wanandroid.mvp.model.api.service;

import com.zev.wanandroid.mvp.model.entity.BannerEntity;
import com.zev.wanandroid.mvp.model.entity.Chapter;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.SetupEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeService {


    @GET("banner/json")
    Observable<BaseArrayEntity<BannerEntity>> getBanner();


    /**
     * 获取文章列表，从0开始
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseEntity<ChapterEntity>> getChapterList(@Path("page") int page);


    /**
     * 获取体系下的文章列表
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseEntity<ChapterEntity>> getChapterListByCid(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取置顶文章
     *
     * @return
     */
    @GET("article/top/json")
    Observable<BaseArrayEntity<Chapter>> getTopChapter();


    /**
     * 公众号tab
     *
     * @return
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseArrayEntity<SetupEntity>> getWxOfficeTab();


    @GET("wxarticle/list/{chapterId}/{page}/json")
    Observable<BaseEntity<ChapterEntity>> getChapterListByWx(@Path("chapterId") int chapterId, @Path("page") int page);


    /**
     * 项目 tab
     *
     * @return
     */
    @GET("project/tree/json")
    Observable<BaseArrayEntity<SetupEntity>> getProjectTab();


    /**
     * 项目列表
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<BaseEntity<ChapterEntity>> getProjectList(@Path("page") int page, @Query("cid") int cid);


    /**
     * 收藏站内文章
     *
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseEntity> addCollectChapter(@Path("id") int id);

    @POST("lg/collect/add/json")
    Observable<BaseEntity> addCollectChapter(@Query("title") String title
            , @Query("author") String author, @Query("link") String link);


    /**
     * 根据文章列表id取消收藏
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseEntity> unCollectByChapter(@Path("id") int id);


    /**
     * 取消我的收藏
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect/{id}/json")
    Observable<BaseEntity> unCollectByMy(@Path("id") int id, @Query("originId") int originId);


    /**
     * 收藏网址
     *
     * @param name
     * @param link
     * @return
     */
    @POST("lg/collect/addtool/json")
    Observable<BaseEntity> addCollectLink(@Query("name") String name, @Query("link") String link);


    /**
     * 删除收藏网址
     *
     * @param id
     * @return
     */
    @POST("lg/collect/deletetool/json")
    Observable<BaseEntity> deleteCollectLink(@Query("id") int id);


    /**
     * 更新收藏网址
     *
     * @param id
     * @param name
     * @param link
     * @return
     */
    @POST("lg/collect/updatetool/json")
    Observable<BaseEntity> updateCollectLink(@Query("id") int id, @Query("name") String name, @Query("link") String link);

}
