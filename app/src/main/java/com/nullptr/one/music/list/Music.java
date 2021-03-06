package com.nullptr.one.music.list;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐bean
 */
public class Music {

    private String id;
    @SerializedName("item_id")
    private String itemId;
    private String title;
    private String forward;
    @SerializedName("img_url")
    private String imageURL;
    @SerializedName("author")
    private Singer singer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    String getImageURL() {
        return imageURL;
    }

    void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    Singer getSinger() {
        return singer;
    }

    void setSinger(Singer singer) {
        this.singer = singer;
    }
}
