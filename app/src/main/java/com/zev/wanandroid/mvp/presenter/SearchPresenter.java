package com.zev.wanandroid.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.utils.RxUtils;
import com.zev.wanandroid.mvp.contract.SearchContract;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.HotSearchEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/27/2020 13:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchPresenter extends BasePresenter<SearchContract.Model, SearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SearchPresenter(SearchContract.Model model, SearchContract.View rootView) {
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


    public void getHotSearch() {
        mModel.getHotSearch()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseArrayEntity<HotSearchEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseArrayEntity<HotSearchEntity> entity) {
                        if (entity.isSuccess()) {
                            AppLifecyclesImpl.getDiskLruCacheUtil().put("hot_search", entity.getData());
                            mRootView.getHotSearch(entity.getData());
                        } else {
                            mRootView.searchError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.searchError();
                    }
                });
    }

    public void getSearch(int page, String key) {
        mModel.getSearch(page, key)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ChapterEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ChapterEntity> entity) {
                        if (entity.getErrorCode() == 0) {
                            if (ObjectUtils.isEmpty(entity.getData().getDatas())) {
                                mRootView.searchNoData();
                                return;
                            }
                            if (entity.getData().getCurPage() == 1) {
                                AppLifecyclesImpl.getDiskLruCacheUtil().put("search_chapter_" + key, entity.getData());
                            }
                            mRootView.getSearch(entity.getData());
                        } else {
                            mRootView.searchError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.searchError();
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
