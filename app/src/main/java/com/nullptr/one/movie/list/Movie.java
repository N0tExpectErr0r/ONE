package com.nullptr.one.movie.list;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION 影视bean
 */
public class Movie {

    private String id;
    @SerializedName("item_id")
    private String itemId;
    private String title;
    private String forward;
    @SerializedName("img_url")
    private String imageURL;
    @SerializedName("last_update_date")
    private String date;
    private Author author;
    @SerializedName("subtitle")
    private String subTitle;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    String getSubTitle() {
        return subTitle;
    }

    void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
