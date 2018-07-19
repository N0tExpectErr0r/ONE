package com.nullptr.one.movie.detail;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION 影视bean
 */
public class MovieDetail {

    @SerializedName("movie_id")
    private String movieId;
    private String title;
    private String content;
    @SerializedName("user")
    private Author author;

    String getMovieId() {
        return movieId;
    }

    void setMovieId(String movieId) {
        this.movieId = movieId;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
