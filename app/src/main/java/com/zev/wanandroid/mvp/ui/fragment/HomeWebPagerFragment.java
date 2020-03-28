package com.zev.wanandroid.mvp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.manager.WebViewManager;

import java.util.List;


public class HomeWebPagerFragment extends Fragment {

    WebViewManager webManager = new WebViewManager();
    FrameLayout webParent;

    public static HomeWebPagerFragment newInstance(String url) {
        HomeWebPagerFragment fragment = new HomeWebPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_pager_web, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webParent = view.findViewById(R.id.fl_web_parent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webManager.destroyWeb();
    }


    private boolean isViewCreated; // 界面是否已创建完成
    private boolean isVisibleToUser; // 是否对用户可见
    private boolean isDataLoaded; // 数据是否已请求

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    protected void lazyLoadData() {
        String url = getArguments().getString("url");
        if (ObjectUtils.isEmpty(url)) return;
        webParent.getLayoutParams().width = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(60);
        webParent.getLayoutParams().height = ScreenUtils.getScreenHeight() / 4 * 3;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        tryLoadData();
    }

    /**
     * 保证在initData后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        isViewCreated = true;
        tryLoadData();
    }


    /**
     * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof HomeWebPagerFragment && isVisibleToUser) {
                tryLoadData();
            }
        }
    }

    public void tryLoadData() {
        if (isViewCreated && isVisibleToUser && !isDataLoaded) {
            lazyLoadData();
            isDataLoaded = true;
            //通知子Fragment请求数据
            dispatchParentVisibleState();
        }
    }
}
