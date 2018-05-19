package com.nullptr.one.presenter.movielist;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION 音乐相关Presenter
 */
public interface MovieListPresenter {

    void loadMovieList();

    void loadMore(String lastId);
}
