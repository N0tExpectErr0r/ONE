package com.nullptr.one.movie.detail;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class MovieDetailList {

    @SerializedName("data")
    private List<MovieDetail> movieDetailList;

    public MovieDetail getMovieDetail() {
        return movieDetailList.get(0);
    }

}
