package com.nullptr.one.net;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public interface HttpListener {

    void onResponse(Response response);

    void onError(String errorMsg);

    void onStart();

    void onFinish();
}
