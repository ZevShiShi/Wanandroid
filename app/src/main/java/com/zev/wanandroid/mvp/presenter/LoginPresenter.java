package com.zev.wanandroid.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.zev.wanandroid.app.utils.RxUtils;
import com.zev.wanandroid.mvp.contract.LoginContract;
import com.zev.wanandroid.mvp.model.entity.UserEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/18/2020 14:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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


    public void loginUser(String username, String pwd) {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(pwd)) {
            ToastUtils.showShort("用户和密码不能为空");
            return;
        }
        mModel.loginUser(username, pwd)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserEntity> entity) {
                        if (entity.isSuccess()) {
                            mRootView.loginSuccess(entity.getData());
                        } else {
                            mRootView.loginFaild(entity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.loginFaild(t.toString());
                    }
                });
    }


    public void regUser(String username, String pwd, String repwd) {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(pwd) || ObjectUtils.isEmpty(repwd)) {
            ToastUtils.showShort("用户和密码不能为空");
            return;
        }
        if (!pwd.equals(repwd)) {
            ToastUtils.showShort("两次密码输入不一致！");
            return;
        }
        mModel.registerUser(username, pwd, repwd)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<UserEntity> entity) {
                        if (entity.isSuccess()) {
                            mRootView.regSuccess(entity.getData());
                        } else {
                            mRootView.regFaild(entity.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.regFaild(t.toString());
                    }
                });


    }
}
