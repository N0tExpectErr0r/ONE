package com.nullptr.one.net;

import java.io.File;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public class KeyValue {

    private String key;

    private Object value;

    public KeyValue(String key, File value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
