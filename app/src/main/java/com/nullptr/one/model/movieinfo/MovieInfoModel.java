package com.nullptr.one.model.movieinfo;

import com.nullptr.one.model.movieinfo.OnMovieInfoListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieInfo对应接口
 */
public interface MovieInfoModel {

    void getMovieInfo(OnMovieInfoListener onMovieInfoListener, String itemId);
}
