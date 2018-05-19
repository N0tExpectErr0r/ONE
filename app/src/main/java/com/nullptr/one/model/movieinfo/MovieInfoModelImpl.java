package com.nullptr.one.model.movieinfo;

import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.model.movieinfo.MovieInfoModel;
import com.nullptr.one.model.movieinfo.OnMovieInfoListener;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieInfoModel接口实现类
 */
public class MovieInfoModelImpl implements MovieInfoModel {

    @Override
    public void getMovieInfo(final OnMovieInfoListener onMovieInfoListener,
            String itemId) {
        HttpUtil.sendHttpRequest("getMovieInfo", itemId, new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                MovieInfo movieInfo = JsonUtil.parseJsonToMovieInfo(response);
                onMovieInfoListener.onSuccess(movieInfo);
            }

            @Override
            public void onError(String errorMsg) {
                onMovieInfoListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMovieInfoListener.onStart();
            }

            @Override
            public void onFinish() {
                onMovieInfoListener.onFinish();
            }
        });
    }
}
