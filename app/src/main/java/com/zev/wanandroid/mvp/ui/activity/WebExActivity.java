package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.EventBusTags;
import com.zev.wanandroid.app.common.EventBusData;
import com.zev.wanandroid.app.manager.WebViewManager;
import com.zev.wanandroid.di.component.DaggerWebExComponent;
import com.zev.wanandroid.mvp.contract.WebExContract;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;
import com.zev.wanandroid.mvp.presenter.WebExPresenter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;
import com.zev.wanandroid.mvp.ui.view.LikeLayout;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import mbanje.kurt.fabbutton.FabButton;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/24/2020 14:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WebExActivity extends BaseMvpActivity<WebExPresenter> implements WebExContract.View {


    @BindView(R.id.fl_web_parent)
    FrameLayout flParent;
    @BindView(R.id.pb_btn)
    FabButton fabButton;
    @BindView(R.id.like_layout)
    LikeLayout likeLayout;

    private WebViewManager webManager = new WebViewManager();
    private String mUrl;
    private boolean collect;
    private int mId;
    private GestureDetector gestureScanner;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebExComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web_ex; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setLightStateMode();
//        BarUtils.setNavBarVisibility(this, false);
        mUrl = getIntent().getStringExtra("url");
        if (ObjectUtils.isEmpty(mUrl)) {
            finish();
            return;
        }
        collect = getIntent().getBooleanExtra("collect", false);
        mId = getIntent().getIntExtra("id", 0);
        fabButton.showProgress(true);
        setFabColor();
        fabButton.setOnClickListener(v -> finish());
        webManager.setupWebViewWithActivity(mUrl, this, flParent, -1, -1, 30, new WebViewManager.WebCallbackEx() {
            @Override
            public void onProgress(WebView view, int newProgress) {
                if (!isFinishing()) {
                    fabButton.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(String title, String url) {

            }

            @Override
            public void onGoWebDetail(String url) {
            }

            @Override
            public void showWebView() {

            }

            @Override
            public void hideWebView() {

            }
        });
        //设置webview自适应屏幕大小
        webManager.getAgentWeb().getAgentWebSettings().getWebSettings()
                .setLoadWithOverviewMode(true);
        webManager.getAgentWeb().getAgentWebSettings().
                getWebSettings().setUseWideViewPort(true);
        webManager.setErrorView(getLayoutInflater().inflate(R.layout.empty_layout, null));

        // 双击webview监听
        gestureScanner = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                likeLayout.addLoveView(e.getRawX(), e.getRawY());
                if (!collect) {
                    mPresenter.addCollect(mId);
                }
                return true;
            }
        });
        webManager.getWebView().setOnTouchListener((v, event) -> gestureScanner.onTouchEvent(event));
    }

    private void setFabColor() {
        fabButton.setColor(collect ? getResources().getColor(R.color.color_ff3f00)
                : getResources().getColor(R.color.white));
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return gestureScanner.onTouchEvent(event);
//    }

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
    public void addCollectChapter(BaseEntity entity) {
        collect = true;
        setFabColor();
        EventBus.getDefault().post(new EventBusData(true, mId), EventBusTags.UPDATE_COLLECT);
        Timber.d("addCollectChapter=========success");
    }

    @Override
    public void collectError(String msg) {
        collect = false;
        Timber.d("addCollectChapter=========error");
    }

    @Override
    protected void onResume() {
        webManager.resumeWeb();
        super.onResume();
    }

    @Override
    protected void onPause() {
        webManager.pauseWeb();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webManager.destroyWeb();
        super.onDestroy();
    }
}
