package com.nullptr.one.movie.detail;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION 影视相关信息bean
 */
public class MovieInfo {

    @SerializedName("id")
    private String movieId;
    @SerializedName("detailcover")
    private String coverURL;
    @SerializedName("title")
    private String movieTitle;
    private String info;
    @SerializedName("officialstory")
    private String story;

    String getMovieId() {
        return movieId;
    }

    void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    String getMovieTitle() {
        return movieTitle;
    }

    void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    String getCoverURL() {
        return coverURL;
    }

    void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    String getStory() {
        return story;
    }

    void setStory(String story) {
        this.story = story;
    }
}
