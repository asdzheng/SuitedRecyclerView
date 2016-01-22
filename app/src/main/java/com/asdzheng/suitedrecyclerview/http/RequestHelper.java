package com.asdzheng.suitedrecyclerview.http;

/**
 * Created by asdzheng on 2016/1/8.
 */

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.asdzheng.suitedrecyclerview.MyApplication;

public class RequestHelper {

    // 默认请求接口的超时时间30s
    private static final int DEFAULT_TIMEOUT_MS = 30 * 1000;

    public static final String SUCCESS_STATUS = "0000";

    private static final long delayTime = 800;

    private final String TAG = this.getClass().getSimpleName();

    private RequestQueue queue;

    private static RequestHelper helper;

    private Context appcontext;

    private RequestHelper() {
        appcontext = MyApplication.context;
        queue = Volley.newRequestQueue(appcontext);
    }

    public static synchronized RequestHelper getInstance() {
        if (helper == null) {
            helper = new RequestHelper();
        }
        return helper;
    }

//    public String requestData(Object tag, HttpResponse http, String next) {
//        String url = UrlUtil.getBaseUrl(next);
//        request(Request.Method.GET, tag, url, http);
//        return url;
//    }

//    /**
//     * @param method 请求的方法，现在都为GET，POST还未做处理
//     * @param tag    为每个请求打的标签，退出界面时可取消请求
//     * @param url    请求的url
//     * @param http   请求的回调接口
//     * @param requestParams  请求需要的参数和返回的的类
//     */
//    @SuppressWarnings("unchecked")
//    private void request(int method, Object tag, final String url, final HttpResponse http) {
//        final String tagName = tag.getClass().getSimpleName();
//
//        LogUtil.w(tagName, "Sending action " + requestParams.get("action") + " request to " + url);
//
//        final long startRequestTime = System.currentTimeMillis();
//
//        GsonRequest request = new GsonRequest(method, url, re.getResponseClass(), new Response.Listener<BaseResponse>() {
//
//            @Override
//            public void onResponse(final BaseResponse baseResponse) {
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                LogUtil.e(tagName, error.toString());
//
//            }
//        }, requestParams);
//
//        request.setTag(tag);
//        // 现在的接口数据都不需要Volley做缓存
//        request.setShouldCache(false);
//        // 重试策略的设置，Volley默认超时时间为2.5s，现改为10s
//        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//        );
//
//        queue.add(request);
//    }

    public void cancelAll(Object tag) {
        queue.cancelAll(tag);
    }

}
