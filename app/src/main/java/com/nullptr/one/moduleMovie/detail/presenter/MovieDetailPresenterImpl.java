package com.nullptr.one.moduleMovie.detail.presenter;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.moduleMovie.detail.IMovieDetail.MovieDetailModel;
import com.nullptr.one.moduleMovie.detail.IMovieDetail.MovieDetailPresenter;
import com.nullptr.one.moduleMovie.detail.IMovieDetail.MovieDetailView;
import com.nullptr.one.moduleMovie.detail.IMovieDetail.OnMovieDetailListener;
import com.nullptr.one.moduleMovie.detail.mdoel.MovieDetailModelImpl;

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
    private Handler mUiHandler;

    public MovieDetailPresenterImpl(MovieDetailView movieDetailView) {
        mMovieDetailView = movieDetailView;
        mMovieDetailModel = new MovieDetailModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getMovieDetail(String itemId) {
        mMovieDetailModel.getMovieDetail(this, itemId);
    }

    @Override
    public void onSuccess(final MovieDetail movieDetail) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.setMovieDetail(movieDetail);
            }
        });

    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.showError(errorMsg);
            }
        });

    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.hideLoading();
            }
        });
    }
}
