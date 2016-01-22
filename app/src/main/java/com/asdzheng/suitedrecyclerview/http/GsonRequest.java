package com.asdzheng.suitedrecyclerview.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.asdzheng.suitedrecyclerview.utils.LogUtil;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asdzheng on 2015/10/31.
 */
public class GsonRequest<T> extends Request<T> {
    private final String TAG = this.getClass().getSimpleName();

    private final Response.Listener<T> mListener;

    private Gson mGson;

    private Class<T> mClass;

    public GsonRequest(int method, String url, Class mClass,  Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        LogUtil.i(TAG, url);
        mListener = listener;
        mGson = new Gson();
        this.mClass =  mClass;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.w(TAG, "Json String = " + jsonString);
            return Response.success(mGson.fromJson(jsonString, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

//    GET /user/4276367/channel/1033563/senses HTTP/1.1
//    Authorization: Token 1448701921-415909295e2e7ab9-1419308
//    User-Agent: same/313
//    X-same-Client-Version: 313
//    machine: android|301|android4.4.2|SM701|864516020267118|1080|1920
//    extrainfo: offical
//    Connection: keep-alive
//    x-same-device-uuid: 864516020267118
//    Host: v2.same.com
//    Accept-Encoding: gzip


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Connection", "keep-alive");
//            hashMap.put("Authorization", "Token " + "1448701921-415909295e2e7ab9-1419308");
//            Log.d("HttpAPI", "Authorization: Token " + LocalUserInfoUtils.getSharedInstance().getUserToken());
        hashMap.put("machine", "android|301|android4.4.2|SM701|864516020267118|1080|1920");
        hashMap.put("extrainfo", "offical");
        hashMap.put("x-same-device-uuid","864516020267118");
        hashMap.put("X-same-Client-Version", "313");
        return hashMap;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
