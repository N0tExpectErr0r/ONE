package com.nullptr.one.movie.detail;


import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailView;
import com.nullptr.one.movie.detail.IMovieDetail.MovieInfoModel;
import com.nullptr.one.movie.detail.IMovieDetail.MovieInfoPresenter;
import com.nullptr.one.movie.detail.IMovieDetail.OnMovieInfoListener;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieInfoPresenter实现类
 */
public class MovieInfoPresenterImpl implements MovieInfoPresenter, OnMovieInfoListener {

    private MovieDetailView mMovieDetailView;
    private MovieInfoModel mMovieInfoModel;
    private Handler mHandler;

    MovieInfoPresenterImpl(MovieDetailView movieDetailView) {
        mMovieDetailView = movieDetailView;
        mMovieInfoModel = new MovieInfoModelImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getMovieInfo(String itemId) {
        mMovieInfoModel.getMovieInfo(this, itemId);
    }

    @Override
    public void onSuccess(final MovieInfo movieInfo) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieDetailView.setMovieInfo(movieInfo);
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
