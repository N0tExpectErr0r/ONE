package com.nullptr.one.movie.detail;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class MovieInfoObject {

    @com.google.gson.annotations.SerializedName("data")
    private MovieInfo movieInfo;

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }
}
