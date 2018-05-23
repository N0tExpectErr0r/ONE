package com.nullptr.one.presenter;

import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.model.moviedetail.MovieDetailModel;
import com.nullptr.one.model.moviedetail.MovieDetailModelImpl;
import com.nullptr.one.model.moviedetail.OnMovieDetailListener;
import com.nullptr.one.presenter.interfaces.DetailPresenter.MovieDetailPresenter;
import com.nullptr.one.view.interfaces.IDetailView.MovieDetailView;

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
    public void onSuccess(MovieDetail movieDetail) {
        mMovieDetailView.setMovieDetail(movieDetail);
    }

    @Override
    public void onFail(String errorMsg) {
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
