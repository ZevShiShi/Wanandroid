package com.zev.wanandroid.app;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.http.GlobalHttpHandler;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by MVPArmsTemplate on 02/17/2020 14:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link okhttp3.Interceptor.Chain}
     * @param response   {@link Response}
     * @return
     */
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
        if (chain.request().url().toString().contains("login")) {
            // 获取服务端cookie
            List<String> cookies = response.headers("Set-Cookie");
            Timber.d("GlobalHttpHandlerImpl onHttpResultResponse cookie=%s", cookies);
            StringBuilder builder = new StringBuilder();
            for (String cookie : cookies) {
                builder.append(cookie + "#");
            }
            SPUtils.getInstance().put("cookie", builder.toString());
        }
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return
     */
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        /* 如果需要在请求服务器之前做一些操作, 则重新构建一个做过操作的 Request 并 return, 如增加 Header、Params 等请求信息, 不做操作则直接返回参数 request
        return chain.request().newBuilder().header("token", tokenId)
                              .build(); */

        // 在请求服务器之前保存cookie，如果已经有cookie则直接保存，并跳转
        String cookie = SPUtils.getInstance().getString("cookie", "");
        Timber.d("GlobalHttpHandlerImpl onHttpRequestBefore cookie=%s", cookie);
        if (TextUtils.isEmpty(cookie)) {
            return request;
        }
        String[] cookies = cookie.split("#");
        Request.Builder builder = chain.request().newBuilder();
        for (int i = 0; i < cookies.length; i++) {
            builder.addHeader("Cookie", cookies[i]);
        }
        return builder.build();
    }
}
