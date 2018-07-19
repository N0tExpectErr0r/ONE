package com.nullptr.one.image;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class ImageIdObject {

    @SerializedName("data")
    private List<String> idList;

    public List<String> getIdList() {
        return idList;
    }

}
