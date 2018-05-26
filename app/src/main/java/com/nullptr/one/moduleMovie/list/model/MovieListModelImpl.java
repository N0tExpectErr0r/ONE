package com.nullptr.one.moduleMovie.list.model;

import com.nullptr.one.bean.Movie;
import com.nullptr.one.moduleMovie.list.IMovieList.MovieListModel;
import com.nullptr.one.moduleMovie.list.IMovieList.OnMoreMovieListener;
import com.nullptr.one.moduleMovie.list.IMovieList.OnMovieListListener;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION MovieModel实现类
 */
public class MovieListModelImpl implements MovieListModel {

    @Override
    public void getList(final OnMovieListListener onMovieListListener) {
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/movie/more/")
                .append("0")
                .append("?platform=android");
        HttpUtil.sendHttpRequest(url.toString(), new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Movie> movieList = JsonUtil.parseJsonToMovieList(response);
                onMovieListListener.onSuccess(movieList);
            }

            @Override
            public void onError(String errorMsg) {
                onMovieListListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMovieListListener.onStart();
            }

            @Override
            public void onFinish() {
                onMovieListListener.onFinish();
            }
        });
    }

    @Override
    public void getMore(final OnMoreMovieListener onMoreMovieListener, String lastId) {
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/movie/more/")
                .append(lastId)
                .append("?platform=android");
        HttpUtil.sendHttpRequest(url.toString(), new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Movie> movieList = JsonUtil.parseJsonToMovieList(response);
                onMoreMovieListener.onMoreSuccess(movieList);
            }

            @Override
            public void onError(String errorMsg) {
                onMoreMovieListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }
        });
    }
}
