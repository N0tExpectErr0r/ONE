package com.nullptr.one;

import android.app.Application;
import android.content.Context;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 用于获取应用程序级Context
 */
public class ContextApplication extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
