package com.nullptr.one.movie.detail;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class MovieInfoObject {

    @SerializedName("data")
    private MovieInfo movieInfo;

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

}
