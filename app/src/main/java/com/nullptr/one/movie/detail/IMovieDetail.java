package com.nullptr.one.movie.detail;

import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.bean.MovieInfo;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IMovieDetail {

    //Model层
    interface MovieDetailModel {

        void getMovieDetail(OnMovieDetailListener onMovieDetailListener, String itemId);
    }

    interface MovieInfoModel {

        void getMovieInfo(OnMovieInfoListener onMovieInfoListener, String itemId);
    }

    interface OnMovieDetailListener {

        void onSuccess(MovieDetail movieDetail);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    interface OnMovieInfoListener {

        void onSuccess(MovieInfo movieInfo);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    //Presenter层
    interface MovieDetailPresenter {

        void getMovieDetail(String itemId);
    }

    interface MovieInfoPresenter {

        void getMovieInfo(String itemId);
    }

    //View层
    interface MovieDetailView {

        void setMovieDetail(MovieDetail musicDetail);

        void setMovieInfo(MovieInfo musicInfo);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }

}
