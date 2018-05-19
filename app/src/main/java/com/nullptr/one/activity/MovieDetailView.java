package com.nullptr.one.activity;

import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.bean.MovieInfo;

/**
 * View层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieDetailView接口，用于对Presenter层的结果做出反馈
 */
public interface MovieDetailView {
    void setMovieDetail(MovieDetail musicDetail);

    void setMovieInfo(MovieInfo musicInfo);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();
}
