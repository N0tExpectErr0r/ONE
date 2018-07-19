package com.nullptr.one.movie.detail;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailModel;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailPresenter;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailView;
import com.nullptr.one.movie.detail.IMovieDetail.OnMovieDetailListener;

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
    private Handler mHandler;

    MovieDetailPresenterImpl(MovieDetailView movieDetailView) {
        mMovieDetailView = movieDetailView;
        mMovieDetailModel = new MovieDetailModelImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getMovieDetail(String itemId) {
        mMovieDetailModel.getMovieDetail(this, itemId);
    }

    @Override
    public void onSuccess(final MovieDetail movieDetail) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.setMovieDetail(movieDetail);
            }
        });

    }

    @Override
    public void onFail(final String errorMsg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.showError(errorMsg);
            }
        });

    }

    @Override
    public void onStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.showLoading();
            }
        });

    }

    @Override
    public void onFinish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.hideLoading();
            }
        });

    }
}
