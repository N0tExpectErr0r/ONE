package com.nullptr.one.movie.detail.presenter;

import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailModel;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailPresenter;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailView;
import com.nullptr.one.movie.detail.IMovieDetail.OnMovieDetailListener;
import com.nullptr.one.movie.detail.model.MovieDetailModelImpl;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieDetailPresenter实现类
 */
public class MovieDetailPresenterImpl implements MovieDetailPresenter, OnMovieDetailListener {

    private MovieDetailView mMovieDetailView;
    private MovieDetailModel mMovieDetailModel;

    public MovieDetailPresenterImpl(MovieDetailView movieDetailView) {
        mMovieDetailView = movieDetailView;
        mMovieDetailModel = new MovieDetailModelImpl();
    }


    @Override
    public void getMovieDetail(String itemId) {
        mMovieDetailModel.getMovieDetail(this, itemId);
    }

    @Override
    public void onSuccess(final MovieDetail movieDetail) {
        mMovieDetailView.setMovieDetail(movieDetail);
    }

    @Override
    public void onFail(final String errorMsg) {
        mMovieDetailView.showError(errorMsg);
    }

    @Override
    public void onStart() {
        mMovieDetailView.showLoading();
    }

    @Override
    public void onFinish() {
        mMovieDetailView.hideLoading();
    }
}
