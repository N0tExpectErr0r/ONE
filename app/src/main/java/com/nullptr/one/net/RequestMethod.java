package com.nullptr.one.net;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public enum RequestMethod {
    GET("GET"),
    POST("POST");

    private String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
