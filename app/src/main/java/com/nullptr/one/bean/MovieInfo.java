package com.nullptr.one.bean;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION 影视相关信息bean
 */
public class MovieInfo {

    private String movieId;
    private String coverURL;
    private String movieTitle;
    private String info;
    private String story;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
