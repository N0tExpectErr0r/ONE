package com.nullptr.one.net;

import android.os.Handler;
import android.os.Looper;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public class RequestExecutor {

    private static RequestExecutor sInstance;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static RequestExecutor getInstance() {
        if (sInstance == null) {
            synchronized (RequestExecutor.class) {
                if (sInstance == null) {
                    sInstance = new RequestExecutor();
                }
            }
        }
        return sInstance;
    }

    /**
     * 执行一个请求
     *
     * @param request 请求对象
     */
    public void execute(Request request, HttpListener httpListener) {
        sHandler.post(new RequestTask(request, httpListener));
    }
}
