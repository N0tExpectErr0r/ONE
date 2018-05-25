package com.nullptr.one.presenter;


import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.model.MovieInfoModelImpl;
import com.nullptr.one.model.interfaces.IDetailListener.OnMovieInfoListener;
import com.nullptr.one.model.interfaces.IDetailModel.MovieInfoModel;
import com.nullptr.one.presenter.interfaces.IDetailPresenter.MovieInfoPresenter;
import com.nullptr.one.view.interfaces.IDetailView.MovieDetailView;

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

    public MovieInfoPresenterImpl(MovieDetailView movieDetailView) {
        mMovieDetailView = movieDetailView;
        mMovieInfoModel = new MovieInfoModelImpl();
    }


    @Override
    public void getMovieInfo(String itemId) {
        mMovieInfoModel.getMovieInfo(this, itemId);
    }

    @Override
    public void onSuccess(MovieInfo movieInfo) {
        mMovieDetailView.setMovieInfo(movieInfo);
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
