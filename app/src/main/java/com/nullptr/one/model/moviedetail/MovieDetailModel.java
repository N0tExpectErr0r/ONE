package com.nullptr.one.model.moviedetail;

import com.nullptr.one.model.moviedetail.OnMovieDetailListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieDetail对应接口
 */
public interface MovieDetailModel {

    void getMovieDetail(OnMovieDetailListener onMovieDetailListener, String itemId);
}
