package com.nullptr.one.net;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public class  Response {

    //响应数据
    private String result;

    //请求过程中发生的错误
    private Exception exception;

    //请求对象

    public Request getRequest() {
        return request;
    }

    private Request request;


    public String getResult() {
        return result;
    }


    public Exception getException() {
        return exception;
    }


    public Response( String result, Exception exception, Request request) {
        this.result = result;
        this.exception = exception;
        this.request = request;
    }
}
