package com.nullptr.one.movie.list.presenter;


import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.movie.list.IMovieList.MovieListModel;
import com.nullptr.one.movie.list.IMovieList.MovieListPresenter;
import com.nullptr.one.movie.list.IMovieList.MovieListView;
import com.nullptr.one.movie.list.IMovieList.OnMoreMovieListener;
import com.nullptr.one.movie.list.IMovieList.OnMovieListListener;
import com.nullptr.one.movie.list.model.MovieListModelImpl;
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
    private Handler mUiHandler;

    public MovieListPresenterImpl(MovieListView movieListView) {
        mMovieListView = movieListView;
        mMovieListModel = new MovieListModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void loadList() {
        mMovieListModel.getList(this);
    }

    @Override
    public void updateList(){mMovieListModel.getListFromNet(this);}

    @Override
    public void loadMore(String lastId) {
        mMovieListModel.getMore(this, lastId);
    }

    @Override
    public void onSuccess(final List<Movie> movieList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieListView.setMovieList(movieList);
            }
        });
    }

    @Override
    public void onMoreSuccess(final List<Movie> movieList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieListView.addMovieList(movieList);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieListView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieListView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMovieListView.hideLoading();
            }
        });
    }
}
