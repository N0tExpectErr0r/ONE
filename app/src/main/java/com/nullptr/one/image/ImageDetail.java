package com.nullptr.one.image;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION 图片详细信息bean
 */
public class ImageDetail {

    @SerializedName("hp_img_url")
    private String imageURL;
    @SerializedName("hp_title")
    private String title;
    @SerializedName("hp_content")
    private String content;

    String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
