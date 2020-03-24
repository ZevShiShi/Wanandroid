package com.zev.wanandroid.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zev.wanandroid.mvp.contract.LinkCollectContract;
import com.zev.wanandroid.mvp.model.api.service.UserService;
import com.zev.wanandroid.mvp.model.entity.LinkEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class LinkCollectModel extends BaseModel implements LinkCollectContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LinkCollectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseArrayEntity<LinkEntity>> getMyLink() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getMyLink();
    }

    @Override
    public Observable<BaseEntity> deleteMyLink(int id) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).deleteMyLink(id);
    }

    @Override
    public Observable<BaseEntity> updateMyLink(int id, String name, String link) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).updateMyLink(id, name, link);
    }
}