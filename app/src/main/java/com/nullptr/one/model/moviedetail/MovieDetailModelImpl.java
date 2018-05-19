package com.nullptr.one.model.moviedetail;

import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.model.moviedetail.MovieDetailModel;
import com.nullptr.one.model.moviedetail.OnMovieDetailListener;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/19
 * @DESCRIPTION MovieDetailModel接口实现类
 */
public class MovieDetailModelImpl implements MovieDetailModel {

    @Override
    public void getMovieDetail(final OnMovieDetailListener onMovieDetailListener,
            String itemId) {
        HttpUtil.sendHttpRequest("getMovieDetail", itemId, new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                MovieDetail movieDetail = JsonUtil.parseJsonToMovieDetail(response);
                onMovieDetailListener.onSuccess(movieDetail);
            }

            @Override
            public void onError(String errorMsg) {
                onMovieDetailListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMovieDetailListener.onStart();
            }

            @Override
            public void onFinish() {
                onMovieDetailListener.onFinish();
            }
        });
    }
}
