package com.nullptr.one.Presenter.movielist;


import com.nullptr.one.bean.Movie;
import com.nullptr.one.fragment.MovieListView;
import com.nullptr.one.model.movielist.MovieListModel;
import com.nullptr.one.model.movielist.MovieListModelImpl;
import com.nullptr.one.model.movielist.OnMoreMovieListener;
import com.nullptr.one.model.movielist.OnMovieListListener;
import java.util.List;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION MoviePresenter实现类
 */
public class MovieListPresenterImpl implements MovieListPresenter, OnMovieListListener,
        OnMoreMovieListener {

    private MovieListView mMovieListView;
    private MovieListModel mMovieListModel;

    public MovieListPresenterImpl(MovieListView movieListView) {
        mMovieListView = movieListView;
        mMovieListModel = new MovieListModelImpl();
    }

    @Override
    public void loadMovieList() {
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
