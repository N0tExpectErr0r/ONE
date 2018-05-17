package com.nullptr.one.model.movielist;

import com.nullptr.one.bean.Movie;
import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnMoreMovieListener {

    void onMoreSuccess(List<Movie> movieList);

    void onFail(String errorMsg);
}
