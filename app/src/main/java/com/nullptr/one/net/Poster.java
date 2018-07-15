package com.nullptr.one.net;


import android.os.Handler;
import android.os.Looper;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public class Poster extends Handler {
    private static Poster instance;

    public static Poster getInstance(){
        if (instance == null){
            synchronized (Poster.class){
                if (instance == null){
                    instance = new Poster();
                }
            }
        }
        return instance;
    }

    private Poster(){
        super(Looper.getMainLooper());
    }
}
