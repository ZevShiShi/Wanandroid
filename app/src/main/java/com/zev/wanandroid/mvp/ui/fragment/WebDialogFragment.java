package com.zev.wanandroid.mvp.ui.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.manager.WebViewManager;
import com.zev.wanandroid.mvp.ui.base.BaseDialogFragment;

/**
 * 一个带Webview的dialog fragment
 */
public class WebDialogFragment extends BaseDialogFragment {

    WebViewManager webManager = new WebViewManager();


    public static WebDialogFragment newInstance(String url) {
        WebDialogFragment fragment = new WebDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {
        String url = getArguments().getString("url");
        if (ObjectUtils.isEmpty(url)) return;
        FrameLayout webParent = view.findViewById(R.id.fl_web_parent);
        webParent.getLayoutParams().width = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(60);
        webParent.getLayoutParams().height = ScreenUtils.getScreenHeight() / 4 * 3;


        ImageView ivClose = view.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> dismiss());

        webManager.setupWebViewWithFragment(url, this, webParent, -1, -1, 30, new WebViewManager.WebCallbackEx() {
            @Override
            public void onProgress(WebView view, int newProgress) {

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
        webManager.getAgentWeb().getAgentWebSettings().getWebSettings()
                .setUseWideViewPort(true);
    }


    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        getDialog().getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 半透明背景
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        });
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webManager.destroyWeb();
    }
}
