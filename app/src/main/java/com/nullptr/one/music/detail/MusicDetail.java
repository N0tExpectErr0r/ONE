package com.nullptr.one.music.detail;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐对应Bean
 */
public class MusicDetail {

    private String id;
    private String title;
    @SerializedName("story_summary")
    private String storySummary;
    @SerializedName("cover")
    private String coverURL;
    @SerializedName("story_title")
    private String storyTitle;
    private String story;
    private String lyric;
    private String info;
    @SerializedName("last_update_date")
    private String date;
    private Author author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getStorySummary() {
        return storySummary;
    }

    void setStorySummary(String storySummary) {
        this.storySummary = storySummary;
    }

    String getCoverURL() {
        return coverURL;
    }

    void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    String getStoryTitle() {
        return storyTitle;
    }

    void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    String getStory() {
        return story;
    }

    void setStory(String story) {
        this.story = story;
    }

    String getLyric() {
        return lyric;
    }

    void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
}
