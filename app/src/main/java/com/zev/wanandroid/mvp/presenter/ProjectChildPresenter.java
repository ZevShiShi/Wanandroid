package com.zev.wanandroid.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.utils.RxUtils;
import com.zev.wanandroid.mvp.contract.ProjectChildContract;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/04/2020 13:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class ProjectChildPresenter extends BasePresenter<ProjectChildContract.Model, ProjectChildContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ProjectChildPresenter(ProjectChildContract.Model model, ProjectChildContract.View rootView) {
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


    public void getProjectList(int page, int cid) {
        mModel.getProjectList(page, cid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ChapterEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ChapterEntity> entity) {
                        if (entity.isSuccess()) {
                            if (entity.getData().getCurPage() == 1) {
                                AppLifecyclesImpl.getDiskLruCacheUtil().put("pro_chapter" + cid, entity.getData());
                            }
                            mRootView.getProjectList(entity.getData());
                        } else {
                            mRootView.getProjectError(entity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.getProjectError(t.getCause().getMessage());
                    }
                });
    }


    public void addCollect(int id) {
        mModel.addCollectChapter(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (entity.getErrorCode() == 0) {
                            mRootView.addCollectChapter(entity);
                        } else {
                            mRootView.collectError(entity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.collectError(t.getCause().getMessage());
                    }
                });
    }


    public void unCollect(int id) {
        mModel.unCollectByChapter(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (entity.getErrorCode() == 0) {
                            mRootView.unCollectChapter(entity);
                        } else {
                            mRootView.collectError(entity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.collectError(t.getCause().getMessage());
                    }
                });
    }
}
