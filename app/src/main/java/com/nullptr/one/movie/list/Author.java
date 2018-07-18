package com.nullptr.one.movie.list;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 作者bean
 */
public class Author {

    @SerializedName("user_name")
    private String name;            //作者名
    private String desc;         //作者详细介绍

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
