package com.nullptr.one.model.moviedetail;

import com.nullptr.one.bean.MovieDetail;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnMovieDetailListener {

    void onSuccess(MovieDetail movieDetail);

    void onFail(String errorMsg);

    void onStart();

    void onFinish();
}
