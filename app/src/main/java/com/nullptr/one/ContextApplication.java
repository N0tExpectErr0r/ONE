package com.nullptr.one;

import android.app.Application;
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 用于获取应用程序级Context
 */
public class ContextApplication extends Application {

    private static Context sContext;
    private static RequestQueue sQueue;

    public static Context getContext() {
        return sContext;
    }

    public static RequestQueue getHttpQueues() {
        return sQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sQueue = Volley.newRequestQueue(getApplicationContext());
    }
}
