package com.nullptr.one.bean;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION 影视bean
 */
public class MovieDetail {

    private String movieId;
    private String title;
    private String content;
    private Author author;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
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
