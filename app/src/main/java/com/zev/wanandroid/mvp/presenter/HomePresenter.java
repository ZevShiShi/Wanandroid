package com.zev.wanandroid.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.utils.RxUtils;
import com.zev.wanandroid.mvp.contract.HomeContract;
import com.zev.wanandroid.mvp.model.entity.BannerEntity;
import com.zev.wanandroid.mvp.model.entity.Chapter;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
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


    public void getBanner() {
        mModel.getBanner()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseArrayEntity<BannerEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseArrayEntity<BannerEntity> arrayEntity) {
                        if (arrayEntity.isSuccess()) {
                            AppLifecyclesImpl.getDiskLruCacheUtil().put("banner", arrayEntity.getData());
                            mRootView.showBanner(arrayEntity.getData());
                        } else {
                            mRootView.bannerFail(arrayEntity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.bannerFail(t.toString());
                    }
                });
    }


    public void getChapterList(int page) {
        mModel.getChapterList(page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ChapterEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ChapterEntity> entity) {
                        if (entity.isSuccess()) {
                            if (entity.getData().getCurPage() == 1) {
                                AppLifecyclesImpl.getDiskLruCacheUtil().put("chapterList", entity.getData().getDatas());
                            }
                            mRootView.getChapterList(entity.getData());
                        } else {
                            mRootView.getChapterError(entity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.getChapterError(t.toString());
                    }
                });
    }


    public void getChapterTop() {
        mModel.getTopChapter()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseArrayEntity<Chapter>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseArrayEntity<Chapter> entity) {
                        if (entity.isSuccess()) {
                            AppLifecyclesImpl.getDiskLruCacheUtil().put("chapterTop", entity.getData());
                            mRootView.getChapterTop(entity.getData());
                        } else {
                            mRootView.getChapterError(entity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.getChapterError(t.toString());
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
