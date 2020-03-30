package com.zev.wanandroid.mvp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.EventBusTags;
import com.zev.wanandroid.app.common.CustomData;
import com.zev.wanandroid.di.component.DaggerLoginComponent;
import com.zev.wanandroid.mvp.contract.LoginContract;
import com.zev.wanandroid.mvp.model.entity.UserEntity;
import com.zev.wanandroid.mvp.presenter.LoginPresenter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 *
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {


    private static final String TAG = "LoginActivity";

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPassword;


    @BindView(R.id.et_reg_username)
    EditText etRegUsername;
    @BindView(R.id.et_reg_pwd)
    EditText etRegPassword;
    @BindView(R.id.et_reg_repwd)
    EditText etRegRepassword;


    @BindView(R.id.ll_reg)
    LinearLayout llReg;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;


    String userName;
    String userpwd;

    @OnClick(R.id.btn_login)
    public void clickLogin(View view) {
        userName = etUsername.getText().toString().trim();
        userpwd = etPassword.getText().toString().trim();
        mPresenter.loginUser(userName, userpwd);
    }

    @OnClick(R.id.btn_register)
    public void clickReg(View view) {
        String userName = etRegUsername.getText().toString().trim();
        String userpwd = etRegPassword.getText().toString().trim();
        String userRepwd = etRegRepassword.getText().toString().trim();
        mPresenter.regUser(userName, userpwd, userRepwd);
    }


    @OnClick(R.id.ll_go_reg)
    public void clickGoReg(View view) {
        Timber.d("我被点击了");
        ObjectAnimator anim = ObjectAnimator.ofFloat(llLogin, "translationX", llLogin.getTranslationX(), 500f);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                llLogin.setTranslationX(0);
                llReg.setVisibility(View.VISIBLE);
                llLogin.setVisibility(View.GONE);
            }
        });
        anim.setDuration(100);
        anim.start();
    }


    @OnClick(R.id.ll_go_login)
    public void clickGoLogin(View view) {
        Timber.d("我被点击了");
        ObjectAnimator anim = ObjectAnimator.ofFloat(llReg, "translationX", llReg.getTranslationX(), -500f);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                llReg.setTranslationX(0);
                llLogin.setVisibility(View.VISIBLE);
                llReg.setVisibility(View.GONE);
            }
        });
        anim.setDuration(100);
        anim.start();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void loginSuccess(UserEntity entity) {
        Timber.d("loginSuccess==%s", entity.toString());
        EventBus.getDefault().post(true, EventBusTags.RELOAD_DATA);
        setResult(CustomData.USER_RESULT);
        finish();
    }

    @Override
    public void loginFaild(String msg) {
        Toast.makeText(getApplication(), "登录失败===>" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regSuccess(UserEntity entity) {
        LogUtils.d(TAG, "reg UserEntity=" + entity.toString());
        ToastUtils.showShort("用户注册成功！");
    }

    @Override
    public void regFaild(String msg) {
        ToastUtils.showShort("注册失败！原因：" + msg);
    }
}
