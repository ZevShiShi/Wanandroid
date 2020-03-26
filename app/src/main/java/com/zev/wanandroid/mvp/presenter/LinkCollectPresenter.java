package com.zev.wanandroid.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.utils.RxUtils;
import com.zev.wanandroid.mvp.contract.LinkCollectContract;
import com.zev.wanandroid.mvp.model.entity.LinkEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
public class LinkCollectPresenter extends BasePresenter<LinkCollectContract.Model, LinkCollectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LinkCollectPresenter(LinkCollectContract.Model model, LinkCollectContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    public void getMyLink() {
        mModel.getMyLink()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseArrayEntity<LinkEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseArrayEntity<LinkEntity> entities) {
                        if (entities.isSuccess()) {
                            Collections.reverse(entities.getData()); // 倒序list
                            AppLifecyclesImpl.getDiskLruCacheUtil().put("collect_link", entities.getData());
                            mRootView.getLink(entities.getData());
                        } else {
                            mRootView.getLink(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.getLinkError(t.getCause().getMessage());
                    }
                });
    }


    public void deleteMyLink(int id) {
        mModel.deleteMyLink(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (entity.getErrorCode() == 0) {
                            mRootView.deleteMyLink();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }


    public void updateMyLink(int id, String name, String link) {
        mModel.updateMyLink(id, name, link)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (entity.getErrorCode() == 0) {
                            mRootView.updateMyLink();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }


}
