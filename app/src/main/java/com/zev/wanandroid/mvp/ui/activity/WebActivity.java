package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.EventBusTags;
import com.zev.wanandroid.app.manager.WebViewManager;
import com.zev.wanandroid.di.component.DaggerWebComponent;
import com.zev.wanandroid.mvp.contract.WebContract;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;
import com.zev.wanandroid.mvp.presenter.WebPresenter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;
import com.zev.wanandroid.mvp.ui.view.PopupWindowWithMask;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class WebActivity extends BaseMvpActivity<WebPresenter> implements WebContract.View {

    @BindView(R.id.fl_web_parent)
    FrameLayout flParent;
    @BindView(R.id.tpv_zan)
    ThumbUpView zanView;
    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.iv_go_web)
    ImageView ivGoWeb;
    @BindView(R.id.iv_web_goback)
    ImageView ivGoBack;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.iv_web_goforward)
    ImageView ivGoForward;

    private WebViewManager webManager = new WebViewManager();
    private boolean swipeBack;
    private boolean collect;
    private String mUrl;
    private String mTitle = "未知";
    private int id;

    private PopupWindowWithMask popupWindow;
    private List<PopBean> popBeanList = new ArrayList<>();


    private void goWeb() {
        String editUrl = etUrl.getText().toString();
        Timber.d("setOnKeyListener===" + editUrl);
        if (editUrl.contains("https") || editUrl.contains("http")) {
            webManager.loadUrl(editUrl);
        } else {
            webManager.loadUrl("https://" + editUrl);
        }
    }

    private void updateCollect() {
        collect = getIntent().getBooleanExtra("collect", false);
//        ToastUtils.showShort("collect=" + collect + "===mUrl==" + mUrl + "===getCurrUrl=" + webManager.getCurrUrl());
        if (collect && mUrl.equals(webManager.getCurrUrl())) {
            zanView.setLike();
        } else {
            zanView.setUnlike();
            collect = false;
        }
    }


    @OnClick(R.id.iv_more)
    public void onMore(View view) {
        View contentView = getLayoutInflater().inflate(R.layout.main_web_pop_item, null);
        popupWindow = new PopupWindowWithMask(this) {
            @Override
            protected View setContentView() {
                return contentView;
            }

            @Override
            protected int setWidth() {
                return ScreenUtils.getScreenWidth();
            }

            @Override
            protected int setHeight() {
                return ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        };

        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(ivMore, Gravity.BOTTOM, 0, 0);
        }


        RecyclerView rvWeb = contentView.findViewById(R.id.rv_web);
        rvWeb.setLayoutManager(new GridLayoutManager(this, 4));

        if (popBeanList.size() == 0) {
            Collections.addAll(popBeanList, new PopBean(R.drawable.web_home, "回到主页")
                    , new PopBean(R.drawable.web_share, "分享到广场")
                    , new PopBean(R.drawable.web_collect, "收藏")
                    , new PopBean(R.drawable.web_refresh, "刷新")
                    , new PopBean(R.drawable.web_return, "边缘返回")
                    , new PopBean(R.drawable.web_exit, "退出")

            );
        }
        WebPopAdapter webPopAdapter = new WebPopAdapter(R.layout.web_pop_item, popBeanList);
        webPopAdapter.bindToRecyclerView(rvWeb);


        swipeBack = SPUtils.getInstance().getBoolean("swipe_back");
        popBeanList.get(popBeanList.size() - 2).checked = swipeBack;
        popBeanList.get(popBeanList.size() - 4).checked = collect;
        webPopAdapter.notifyDataSetChanged();

        webPopAdapter.setOnItemClickListener((adapter, view1, position) -> {
            ImageView ivIcon = (ImageView) adapter.getViewByPosition(position, R.id.iv_web_icon);

            switch (position) {
                case 0:  // 回到主页
                    webManager.loadUrl(mUrl);
                    popupWindow.dismiss();
                    break;
                case 1:// 分享到广场
                    break;
                case 2:// 收藏
                    if (mUrl.equals(webManager.getCurrUrl())) {
                        // 收藏站内文章
                        if (!collect) {
                            mPresenter.addCollect(id);
                            ivIcon.setSelected(true);
                        } else {
                            mPresenter.unCollect(id);
                            ivIcon.setSelected(false);
                        }
                    } else {
                        // 收藏网址
                        if (!collect) {
                            mPresenter.addCollectLink(mTitle, webManager.getCurrUrl());
                            ivIcon.setSelected(true);
                            zanView.setLike();
                        } else {
                            ivIcon.setSelected(false);
                            zanView.setUnlike();
                        }
                    }

                    break;
                case 3:// 刷新
                    webManager.reloadUrl();
                    popupWindow.dismiss();
                    break;
                case 4:// 边缘返回
                    setSwipeBackEnable(swipeBack = !swipeBack);
                    ivIcon.setSelected(swipeBack);
                    SPUtils.getInstance().put("swipe_back", swipeBack);
                    break;
                case 5:// 退出
                    popupWindow.dismiss();
                    finish();
                    break;

            }
        });


        contentView.findViewById(R.id.iv_close).setOnClickListener(v -> {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        });

        contentView.findViewById(R.id.iv_web_share).setOnClickListener(v -> {
            startActivity(IntentUtils.getShareTextIntent(etUrl.getText().toString(), true));
        });
    }


    @OnClick(R.id.iv_web_goback)
    public void onGoBack(View view) {
        goBack();
    }

    @OnClick(R.id.iv_go_web)
    public void onGoWeb(View view) {
        goWeb();
    }

    @OnClick(R.id.iv_web_goforward)
    public void onGoForward(View view) {
        goForward();
    }

    private void goForward() {
        webManager.goForward();
    }

    private void goBack() {
        if (webManager.getWebView().canGoBack()) {
            webManager.goBack();
        } else {
            finish();
        }
    }


    private void updateGoBack() {
        if (webManager.getWebView().canGoBack()) {
            ivGoBack.setImageResource(R.drawable.web_left_arrow);
        } else {
            ivGoBack.setImageResource(R.drawable.close);
        }
    }

    private void updateGoForward() {
        if (webManager.getWebView().canGoForward()) {
            ivGoForward.setEnabled(true);
        } else {
            ivGoForward.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webManager.getWebView().canGoBack()) {
            webManager.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webManager.destroyWeb();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        swipeBack = SPUtils.getInstance().getBoolean("swipe_back");
        mUrl = getIntent().getStringExtra("url");
        id = getIntent().getIntExtra("id", 0);

        setSwipeBackEnable(swipeBack);
        collect = getIntent().getBooleanExtra("collect", false);
        if (collect) {
            zanView.setLike();
        } else {
            zanView.setUnlike();
        }

        zanView.setOnThumbUp(like -> {
            if (like) {
                mPresenter.addCollect(id);
            } else {
                mPresenter.unCollect(id);
            }
        });

        if (ObjectUtils.isEmpty(mUrl)) {
            finish();
            return;
        }

        etUrl.setText(mUrl);
        etUrl.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !isFinishing()) {
                etUrl.setText(webManager.getCurrUrl());
            }
        });

        etUrl.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                goWeb();
            }
            return false;
        });

        webManager.setupWebViewWithActivity(mUrl, this, flParent, -1, -1, 30, new WebViewManager.WebCallbackEx() {
            @Override
            public void onProgress(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedTitle(String title, String url) {
                mTitle = title;
                etUrl.setText(title);
            }

            @Override
            public void onGoWebDetail(String url) {
                webManager.loadUrl(url);
            }

            @Override
            public void showWebView() {
                updateGoBack();
                updateGoForward();
                updateCollect();
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


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void addCollectChapter(BaseEntity entity) {
        zanView.setLike();
        collect = true;
        EventBus.getDefault().post(true, EventBusTags.UPDATE_COLLECT);
    }

    @Override
    public void unCollectChapter(BaseEntity entity) {
        zanView.setUnlike();
        collect = false;
        EventBus.getDefault().post(false, EventBusTags.UPDATE_COLLECT);
    }

    @Override
    public void collectError(String msg) {
        zanView.setUnlike();
        collect = false;
    }

    @Override
    public void addCollectLink(BaseEntity entity) {
        collect = true;
    }


    private class WebPopAdapter extends BaseQuickAdapter<PopBean, BaseViewHolder> {

        public WebPopAdapter(int layoutResId, @Nullable List<PopBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PopBean item) {
            helper.setImageResource(R.id.iv_web_icon, item.resId);
            helper.setText(R.id.tv_web_text, item.name);

            ImageView icon = helper.getView(R.id.iv_web_icon);
            if (item.name.equals("边缘返回")) {
                icon.setBackgroundResource(R.drawable.web_pop_selector);
                icon.setSelected(item.checked);
            } else if (item.name.equals("收藏")) {
                icon.setBackgroundResource(R.drawable.web_pop_selector);
                icon.setSelected(item.checked);
            }
        }
    }

    class PopBean {
        public int resId;
        public String name;
        public boolean checked;

        public PopBean(int resId, String name) {
            this.resId = resId;
            this.name = name;
        }
    }

}
