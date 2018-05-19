package com.nullptr.one.model.movieinfo;

import com.nullptr.one.bean.MovieInfo;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnMovieInfoListener {

    void onSuccess(MovieInfo movieInfo);

    void onFail(String errorMsg);

    void onStart();

    void onFinish();
}
