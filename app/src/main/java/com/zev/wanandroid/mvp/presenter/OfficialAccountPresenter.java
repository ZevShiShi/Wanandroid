package com.zev.wanandroid.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.utils.RxUtils;
import com.zev.wanandroid.mvp.contract.OfficialAccountContract;
import com.zev.wanandroid.mvp.model.entity.SetupEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class OfficialAccountPresenter extends BasePresenter<OfficialAccountContract.Model, OfficialAccountContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OfficialAccountPresenter(OfficialAccountContract.Model model, OfficialAccountContract.View rootView) {
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

    public void getWxTab() {
        mModel.getWxOfficeTab()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseArrayEntity<SetupEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseArrayEntity<SetupEntity> entity) {
                        if (entity.isSuccess()) {
                            AppLifecyclesImpl.getDiskLruCacheUtil().put("wx_tab", entity.getData());
                            mRootView.getWxTab(entity.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ToastUtils.showShort(t.getCause().getMessage());
                    }
                });
    }

}
