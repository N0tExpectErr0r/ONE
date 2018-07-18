package com.nullptr.one.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nullptr.one.ContextApplication;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public class RequestTask implements Runnable {

    private Request request;

    private HttpListener httpListener;
    private Listener<String> mSuccessListener = new Listener<String>() {
        @Override
        public void onResponse(String responseText) {
            //收到响应数据
            Response response = new Response(responseText, null, request);
            httpListener.onResponse(response);
            httpListener.onFinish();
        }
    };
    private ErrorListener mErrorListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //错误回调
            httpListener.onError("网络错误，请检查网络设置");
        }
    };

    public RequestTask(Request request, HttpListener httpListener) {
        this.request = request;
        this.httpListener = httpListener;
    }

    @Override
    public void run() {
        httpListener.onStart();
        String urlStr = request.getUrl();
        RequestMethod method = request.getMethod();
        StringRequest stringRequest = null;
        switch (method.value()) {
            case "GET":
                stringRequest = new StringRequest(Method.GET, urlStr, mSuccessListener, mErrorListener);
                break;
            case "POST":
                stringRequest = new StringRequest(Method.POST, urlStr, mSuccessListener, mErrorListener) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        for (KeyValue keyValue : request.getKeyValues()) {
                            params.put(keyValue.getKey(), keyValue.getValue().toString());
                        }
                        return params;
                    }
                };
                break;
            default:
                throw new IllegalArgumentException("请求方式填写错误");
        }
        ContextApplication.getHttpQueues().add(stringRequest);
    }
}
