package com.nullptr.one.image;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class ImageObject {

    @SerializedName("data")
    private ImageDetail imageDetail;


    public ImageDetail getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(ImageDetail imageDetail) {
        this.imageDetail = imageDetail;
    }
}
