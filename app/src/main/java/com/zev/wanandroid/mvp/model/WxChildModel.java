package com.zev.wanandroid.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zev.wanandroid.mvp.contract.WxChildContract;
import com.zev.wanandroid.mvp.model.api.service.HomeService;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/02/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class WxChildModel extends BaseModel implements WxChildContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WxChildModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<ChapterEntity>> getChapterListByWx(int chapterId, int page) {
        return mRepositoryManager.obtainRetrofitService(HomeService.class).getChapterListByWx(chapterId, page);
    }

    @Override
    public Observable<BaseEntity> addCollectChapter(int id) {
        return mRepositoryManager.obtainRetrofitService(HomeService.class).addCollectChapter(id);
    }

    @Override
    public Observable<BaseEntity> unCollectByChapter(int id) {
        return mRepositoryManager.obtainRetrofitService(HomeService.class).unCollectByChapter(id);
    }

}