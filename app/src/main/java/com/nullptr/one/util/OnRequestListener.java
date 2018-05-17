package com.nullptr.one.util;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/10
 * @DESCRIPTION Http请求回调接口
 */

public interface OnRequestListener {

    void onResponse(String response);   //请求结果回调

    void onError(String errorMsg);      //请求失败回调

    void onStart();                     //请求开始回调

    void onFinish();                    //请求结束回调
}