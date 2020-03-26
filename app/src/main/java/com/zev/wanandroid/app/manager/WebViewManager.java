package com.zev.wanandroid.app.manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.AgentWebUIControllerImplBase;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;

import timber.log.Timber;

/**
 * 一个基于AgentWeb框架封装的WebView
 */
public class WebViewManager {

    private static final String TAG = "WebViewManager";
    private AgentWeb mAgentWeb;
    private boolean isRefresh = true;
    private WebCallback callback;
    private View errorView;
    private Activity activity;
    private String mUrl;


    public WebViewManager() {
    }

    /**
     * 初始化在fragment中的webview
     *
     * @param f          fragment
     * @param parentView web容器
     * @param width      web宽
     * @param height     web高
     * @param progress   加载进度，用于控制什么0-100，用于控制什么时候显示webView
     */
    public void setupWebViewWithFragment(Fragment f, ViewGroup parentView, int width, int height
            , int progress, WebCallback callback) {
        if (f == null || parentView == null
                || width == 0 || height == 0
                || progress == 0 || callback == null) {
            return;
        }
        this.callback = callback;
        mAgentWeb = AgentWeb.with(f)//(FrameLayout) view) new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAgentWebParent(parentView, -1, new LinearLayout.LayoutParams(width, height))//传入AgentWeb的父控件。
                .closeIndicator()
//                .setAgentWebWebSettings(getSettings())
//                .useDefaultIndicator(f.getResources().getColor(R.color.color_FE3D40), ConvertUtils.dp2px(2))//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setWebViewClient(new AgentWebViewClient())//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(new AgentWebChromeClient(progress)) //WebChromeClient
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(getAgentWebUiController())
//                .setMainFrameErrorView(R.layout.web_net_error_layout, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .get(); // 不使用go，防止第一次载入的时候跳转到webViewActivity
//        mAgentWeb2 = new SoftReference<>(mAgentWeb);
        if (AppUtils.isAppDebug()) {
            AgentWebConfig.debug();
        }
    }

//    private IAgentWebSettings getSettings() {
//        return new IAgentWebSettings() {
//
//            WebSettings mWebSettings;
//
//            @Override
//            public IAgentWebSettings toSetting(WebView webView) {
//                settings(webView);
//                return this;
//            }
//
//            @Override
//            public WebSettings getWebSettings() {
//                return mWebSettings;
//            }
//
//
//            private void settings(WebView webView) {
//                mWebSettings = webView.getSettings();
//                mWebSettings.setJavaScriptEnabled(true);
//                mWebSettings.setSupportZoom(true);
//                mWebSettings.setBuiltInZoomControls(false);
//                mWebSettings.setSavePassword(false);
//                if (AgentWebUtils.checkNetwork(webView.getContext())) {
//                    //根据cache-control获取数据。
//                    mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//                } else {
//                    //没网，则从本地获取，即离线加载
//                    mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    //适配5.0不允许http和https混合使用情况
//                    mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//                    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                }
//                mWebSettings.setTextZoom(100);
//                mWebSettings.setDatabaseEnabled(true);
//                mWebSettings.setAppCacheEnabled(true);
//                mWebSettings.setLoadsImagesAutomatically(true);
//                mWebSettings.setSupportMultipleWindows(false);
//                // 是否阻塞加载网络图片  协议http or https
//                mWebSettings.setBlockNetworkImage(false);
//                // 允许加载本地文件html  file协议
//                mWebSettings.setAllowFileAccess(true);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
//                    mWebSettings.setAllowFileAccessFromFileURLs(false);
//                    // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
//                    mWebSettings.setAllowUniversalAccessFromFileURLs(false);
//                }
//                mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//                    mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); // SINGLE_COLUMN
//                } else {
//                    mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//                }
//
//                mWebSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//                mWebSettings.setLoadWithOverviewMode(false);
//                mWebSettings.setUseWideViewPort(true);
//                mWebSettings.setDomStorageEnabled(true);
//                mWebSettings.setNeedInitialFocus(true);
//                mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//                mWebSettings.setDefaultFontSize(16);
//                mWebSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
//                mWebSettings.setGeolocationEnabled(true);
//                String dir = AgentWebConfig.getCachePath(webView.getContext());
//                com.just.agentweb.LogUtils.i(TAG, "dir:" + dir + "   appcache:" + AgentWebConfig.getCachePath(webView.getContext()));
//                //设置数据库路径  api19 已经废弃,这里只针对 webkit 起作用
//                mWebSettings.setGeolocationDatabasePath(dir);
//                mWebSettings.setDatabasePath(dir);
//                mWebSettings.setAppCachePath(dir);
//                //缓存文件最大值
//                mWebSettings.setAppCacheMaxSize(Long.MAX_VALUE);
////                mWebSettings.setUserAgentString(getWebSettings()
////                        .getUserAgentString()
////                        .concat(USERAGENT_AGENTWEB)
////                        .concat(USERAGENT_UC)
////                );
//                com.just.agentweb.LogUtils.i(TAG, "UserAgentString : " + mWebSettings.getUserAgentString());
//            }
//        };
//
//
//    }

    private AgentWebUIControllerImplBase getAgentWebUiController() {
        return new AgentWebUIControllerImplBase() {
            @Override
            public void onMainFrameError(WebView view, int errorCode, String description, String failingUrl) {
                // FIXME: 2019-07-17 由于框架作者没有修复超时或者网络错误页面，导致错误提示页面不消失的问题，待修复
                LogUtils.d(TAG, "onMainFrameError===" + errorCode
                        + ",description===" + description + ",failingUrl===" + failingUrl);
//                if (failingUrl.contains("weixin")) {
//                    // 避免出现默认的错误界面
//                    if (mAgentWeb != null) {
//                        mAgentWeb.getUrlLoader().loadUrl(getUrl());
//                    }
//                    return;
//                }
//                isError = true;
//                if (errorView != null) {
//                    // 避免出现默认的错误界面
//                    if (mAgentWeb != null) {
//                        mAgentWeb.getUrlLoader().loadUrl("about:blank");
//                    }
//                    errorView.setVisibility(View.VISIBLE);
//                }
            }
        };
    }

    /**
     * 初始化在fragment中的webview
     *
     * @param url        url链接
     * @param f          fragment
     * @param parentView web容器
     * @param width      web宽
     * @param height     web高
     * @param progress   加载进度，用于控制什么0-100，用于控制什么时候显示webView
     */
    public void setupWebViewWithFragment(String url, Fragment f, ViewGroup parentView, int width, int height, int progress, WebCallback callback) {
        if (url == null || f == null || parentView == null || width == 0 || height == 0 || callback == null)
            return;
        this.mUrl = url;
        this.callback = callback;
        mAgentWeb = AgentWeb.with(f)//(FrameLayout) view) new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAgentWebParent(parentView, -1, new LinearLayout.LayoutParams(width, height))//传入AgentWeb的父控件。
                .closeIndicator()
//                .useDefaultIndicator(f.getResources().getColor(R.color.tool_bar_color), -1)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setWebViewClient(new AgentWebViewClient())//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(new AgentWebChromeClient(progress)) //WebChromeClient
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(getAgentWebUiController())
//                .setMainFrameErrorView(R.layout.web_net_error_layout, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(url); //WebView载入该url地址的页面并显示。

        if (AppUtils.isAppDebug()) {
            AgentWebConfig.debug();
        }
    }


    /**
     * 初始化在activity中的webview
     *
     * @param url        url链接
     * @param activity   activity
     * @param parentView web容器
     * @param width      web宽
     * @param height     web高
     * @param progress   加载进度，用于控制什么0-100，用于控制什么时候显示webView
     */
    public void setupWebViewWithActivity(String url, Activity activity, ViewGroup parentView, int width, int height, int progress, WebCallback callback) {
        if (url == null || activity == null || parentView == null || width == 0 || height == 0)
            return;
        this.mUrl = url;
        this.activity = activity;
        this.callback = callback;
        mAgentWeb = AgentWeb.with(activity)//(FrameLayout) view) new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAgentWebParent(parentView, -1, new LinearLayout.LayoutParams(width, height))//传入AgentWeb的父控件。
                .closeIndicator()
//                .useDefaultIndicator(activity.getResources().getColor(R.color.tool_bar_color), -1)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
//                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(new AgentWebViewClient())//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(new AgentWebChromeClient(progress)) //WebChromeClient
//                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(getAgentWebUiController())
//                .setMainFrameErrorView(R.layout.web_net_error_layout, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
//                .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
//                .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
//                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
//                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
//                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
//                .get(); // 不使用go，防止第一次载入的时候跳转到webViewActivity
                .go(url); //WebView载入该url地址的页面并显示。
        if (AppUtils.isAppDebug()) {
            AgentWebConfig.debug();
        }
    }


    /**
     * 初始化在activity中的webview
     *
     * @param activity   activity
     * @param parentView web容器
     * @param width      web宽
     * @param height     web高
     * @param progress   加载进度，用于控制什么0-100，用于控制什么时候显示webView
     */
    public AgentWeb setupWebViewWithActivity(Activity activity, ViewGroup parentView, int width, int height, int progress, WebCallback callback) {
        if (activity == null || parentView == null || width == 0 || height == 0) return null;
        this.activity = activity;
        this.callback = callback;
        mAgentWeb = AgentWeb.with(activity)//(FrameLayout) view) new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAgentWebParent(parentView, -1, new LinearLayout.LayoutParams(width, height))//传入AgentWeb的父控件。
                .closeIndicator()
//                .useDefaultIndicator(getResources().getColor(R.color.color_FE3D40), 0)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
//                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(new AgentWebViewClient())//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(new AgentWebChromeClient(progress)) //WebChromeClient
//                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(getAgentWebUiController())
//                .setMainFrameErrorView(R.layout.web_net_error_layout, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
//                .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
//                .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
//                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
//                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
//                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .get(); // 不使用go，防止第一次载入的时候跳转到webViewActivity
        if (AppUtils.isAppDebug()) {
            AgentWebConfig.debug();
        }
        return mAgentWeb;
    }


    public WebView getWebView() {
        try {
            if (mAgentWeb != null) {
                return mAgentWeb.getWebCreator().getWebView();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getWebView error=" + e.toString());
        }
        return null;
    }

    public AgentWeb getAgentWeb() {
        if (mAgentWeb != null) {
            return mAgentWeb;
        }
        return null;
    }


//    public void clearHistory() {
//        if (mAgentWeb != null) {
//            mAgentWeb.getWebCreator().getWebView().clearHistory();
//            mAgentWeb.getWebCreator().getWebView().clearCache(true);
//        }
//    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return false;
    }

    public void reloadUrl() {
        if (mAgentWeb != null) {
            mAgentWeb.getUrlLoader().reload();
        }
    }


    /**
     * 加载url
     * https://cpu.baidu.com/1022/eecb6322?scid=27508
     *
     * @param url
     */
    public void loadUrl(String url) {
        if (mAgentWeb != null) {
            mAgentWeb.getUrlLoader().loadUrl(url);
        }
    }

    public void resumeWeb() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    public void pauseWeb() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    public void destroyWeb() {
        if (mAgentWeb != null) {
            mAgentWeb.destroy();
            mAgentWeb = null;
        }
    }


    public void setErrorView(View errorView) {
        if (errorView == null) return;
        this.errorView = errorView;
        this.errorView.setOnClickListener(v -> {
            errorView.setVisibility(View.GONE);
            callback.hideWebView();
            loadUrl(mUrl);
        });
    }


    public String getCurrUrl() {
        if (getWebView() == null) return "";
        return getWebView().getUrl();
    }


    /**
     * 后退
     * <p>
     * 解决部分页面点击快速点击两次才能返回
     *
     * @return
     */
    public void goBack() {
        String url = getCurrUrl();
        getWebView().goBack();
        // 如果遇到迷之无法返回上一页
        if (getCurrUrl().equals(url)) {
            getWebView().goBack();
        }
    }


    /**
     * 前进
     *
     * @return
     */
    public boolean goForward() {
        if (getWebView() != null) {
            if (getWebView().canGoForward()) {
                getWebView().goForward();
                return true;
            }
        }
        return false;
    }


    class AgentWebViewClient extends com.just.agentweb.WebViewClient {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
            LogUtils.d(TAG, "7.0以上 shouldOverrideUrlLoading:" + request.getUrl().toString());
            return shouldOverrideUrlLoading(view, request.getUrl().toString());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }


        @Override
        public boolean shouldOverrideUrlLoading(final android.webkit.WebView view, String url) {
            isRefresh = false;
            String httpStr = url.substring(0, url.indexOf(":"));
            LogUtils.d(TAG, "view:" + new Gson().toJson(view.getHitTestResult()));
            LogUtils.d(TAG, "shouldOverrideUrlLoading:" + url);
            LogUtils.d(TAG, "shouldOverrideUrlLoading httpStr=" + httpStr);

//            android.webkit.WebView.HitTestResult hitTestResult = view.getHitTestResult();
//            // 重定向
//            if (hitTestResult.getType() != WebView.HitTestResult.UNKNOWN_TYPE) {
//                if (callback != null) {
//                    if (url.contains("cpro.baidu.com")) {
//                        LogUtils.d(TAG, "zhujiang WebView2Activity……………………………………………………" + url);
//                        callback.onGoWebDetail(url);
//                        return false; // false
//                    }
//                    LogUtils.d(TAG, "zhujiang 其他界面……………………………………………………" + activity + "---" + "---" + url);
//                    callback.onGoWebDetail(url);
//                    return true; // true
//                }
//            }

            LogUtils.d(TAG, "zhujiang……………………………………………………" + activity + "---" + "---" + url);
            if (url.startsWith(httpStr)) {
                callback.onGoWebDetail(url);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                ActivityUtils.startActivity(intent);
            }
            return true;
        }

        @Override
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            LogUtils.d(TAG, "onPageStarted mUrl:" + url + " onPageStarted  target:" + url);
            if (errorView != null)
                errorView.setVisibility(View.GONE);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
        }

        @Override
        public void onReceivedError(android.webkit.WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Timber.e("onReceivedError====" + errorCode + "===" + description + "===" + failingUrl);
//            loadUrl("about:blank");// 清除掉默认错误页内容
            if (errorView != null)
                errorView.setVisibility(View.VISIBLE); // 当加载网页错误时，显示mErrorFrame的内容
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            handler.proceed();// 接受所有网站的证书
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.d(TAG, "  onPageFinished mUrl:" + url);
            if (callback != null)
                callback.showWebView();
        }

        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
//        public void onMainFrameError(AbsAgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String failingUrl) {
//            LogUtils.d(TAG, "AgentWebFragment onMainFrameError");
//            agentWebUIController.onMainFrameError(view, errorCode, description, failingUrl);
//        }


        @Override
        public void onReceivedHttpError(android.webkit.WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            LogUtils.d(TAG, "onReceivedHttpError:" + 3 + "  request:" + new Gson().toJson(request) + "  errorResponse:" + new Gson().toJson(errorResponse));
        }
    }

//    private void loadError() {
//        if (vs != null) {
//            errorView = vs.inflate().findViewById(R.id.ll_web_error);
//            errorView.setVisibility(View.VISIBLE); // 当加载网页错误时，显示mErrorFrame的内容
//
//            errorView.setOnClickListener(v -> {
//                errorView.setVisibility(View.GONE);
//                callback.hideWebView();
//                loadUrl(mUrl);
//            });
//            errorView.findViewById(R.id.btn_reload).setOnClickListener(v -> {
//                errorView.setVisibility(View.GONE);
//                callback.hideWebView();
//                loadUrl(mUrl);
//            });
//        }
//    }


    class AgentWebChromeClient extends WebChromeClient {

        private int progress;

        public AgentWebChromeClient(int progress) {
            this.progress = progress;
        }


        @Override
        public void onProgressChanged(android.webkit.WebView view, int newProgress) {
            if (callback == null) return;
            if (!isRefresh) return; // 跳转不刷新，防止闪屏
            if (callback instanceof WebCallbackEx) {
                ((WebCallbackEx) callback).onProgress(view, newProgress);
            }
            LogUtils.d(TAG, "onProgressChanged:" + newProgress + "  url:" + view.getUrl());
            if (newProgress >= progress) {
                // 加载完成隐藏加载布局，显示新闻布局
                callback.showWebView();
            }
        }

        @Override
        public void onReceivedTitle(android.webkit.WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogUtils.d(TAG, "onReceivedTitle:" + title + "  view:" + view.getUrl());
            if (callback != null && callback instanceof WebCallbackEx) {
                ((WebCallbackEx) callback).onReceivedTitle(title, view.getUrl());
            }
//            if (ObjectUtils.isNotEmpty(title)) {
//                callback.showWebView();
//            }
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            LogUtils.d(TAG, "WebChromeClient1 onShowCustomView==================" + view);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            LogUtils.d(TAG, "WebChromeClient1 onHideCustomView=============");
        }
    }


    public interface WebCallback {
        void onGoWebDetail(String url);

        void showWebView();

        void hideWebView();
    }

    public interface WebCallbackEx extends WebCallback {
        void onProgress(android.webkit.WebView view, int newProgress);

        void onReceivedTitle(String title, String url);
    }

}
