package com.nullptr.one.net;

import android.util.Log;
import javax.net.ssl.HttpsURLConnection;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public class Message implements Runnable{

    private Response response;

    private HttpListener httpListener;

    public Message(Response response, HttpListener httpListener) {
        this.response = response;
        this.httpListener = httpListener;
    }

    @Override
    public void run() {
        //回调到主线程
        Exception exception = response.getException();
        if (exception == null&&response.getResponseCode() == HttpsURLConnection.HTTP_OK){
            httpListener.onResponse(response);
            httpListener.onFinish();
        }else{
            httpListener.onError(exception);
        }

    }
}
