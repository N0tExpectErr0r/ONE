package com.nullptr.one.fragment;

import com.nullptr.one.bean.Movie;
import java.util.List;

/**
 * View层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION MovieListView接口，用于对Presenter层的结果做出反馈
 */
public interface MovieListView {

    void setMovieList(List<Movie> movieList);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();

    void addMovieList(List<Movie> movieList);
}
