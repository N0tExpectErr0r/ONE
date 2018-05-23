package com.nullptr.one.presenter;


import com.nullptr.one.bean.Movie;
import com.nullptr.one.model.movielist.MovieListModel;
import com.nullptr.one.model.movielist.MovieListModelImpl;
import com.nullptr.one.model.movielist.OnMoreMovieListener;
import com.nullptr.one.model.movielist.OnMovieListListener;
import com.nullptr.one.presenter.interfaces.ListPresenter;
import com.nullptr.one.view.interfaces.IListView.MovieListView;
import java.util.List;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION MoviePresenter实现类
 */
public class MovieListPresenterImpl implements ListPresenter, OnMovieListListener,
        OnMoreMovieListener {

    private MovieListView mMovieListView;
    private MovieListModel mMovieListModel;

    public MovieListPresenterImpl(MovieListView movieListView) {
        mMovieListView = movieListView;
        mMovieListModel = new MovieListModelImpl();
    }

    @Override
    public void loadList() {
        mMovieListModel.getList(this);
    }

    @Override
    public void loadMore(String lastId) {
        mMovieListModel.getMore(this, lastId);
    }

    @Override
    public void onSuccess(List<Movie> movieList) {
        mMovieListView.setMovieList(movieList);
    }

    @Override
    public void onMoreSuccess(List<Movie> movieList) {
        mMovieListView.addMovieList(movieList);
    }

    @Override
    public void onFail(String errorMsg) {
        mMovieListView.showError(errorMsg);
    }

    @Override
    public void onStart() {
        mMovieListView.showLoading();
    }

    @Override
    public void onFinish() {
        mMovieListView.hideLoading();
    }
}
