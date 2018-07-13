package com.nullptr.one.movie.list;

import com.nullptr.one.bean.Movie;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IMovieList {

    //Model层
    interface MovieListModel {

        void getList(OnMovieListListener listener);

        void getMore(OnMoreMovieListener onMoreMovieListener, String lastId);

        void getListFromNet(OnMovieListListener onMovieListListener);
    }

    interface OnMovieListListener {

        void onSuccess(List<Movie> movieList);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }


    interface OnMoreMovieListener {

        void onMoreSuccess(List<Movie> movieList);

        void onFail(String errorMsg);
    }

    //Presenter层
    interface MovieListPresenter {

        void loadList();

        void updateList();

        void loadMore(String lastId);
    }

    //View层
    interface MovieListView {

        void setMovieList(List<Movie> movieList);

        void addMovieList(List<Movie> movieList);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }
}
