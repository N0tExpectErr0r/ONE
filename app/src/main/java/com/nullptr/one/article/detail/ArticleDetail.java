package com.nullptr.one.article.detail;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class ArticleDetail {


    @SerializedName("content_id")
    private String contentId;
    @SerializedName("hp_title")
    private String title;
    @SerializedName("hp_author")
    private String authorName;
    @SerializedName("auth_it")
    private String authorDesc;
    @SerializedName("hp_content")
    private String content;
    @SerializedName("last_update_date")
    private String date;
    private String copyright;

    public String getContentId() {
        return contentId;
    }

    void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDesc() {
        return authorDesc;
    }

    void setAuthorDesc(String authorDesc) {
        this.authorDesc = authorDesc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCopyright() {
        return copyright;
    }

    void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}

