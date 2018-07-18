package com.nullptr.one.movie.detail;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class MovieDetailObject {

    @SerializedName("data")
    private MovieDetailList movieDetailList;

    public MovieDetailList getMovieDetailList() {
        return movieDetailList;
    }

    public void setMovieDetail(MovieDetailList movieDetail) {
        this.movieDetailList = movieDetail;
    }

}
