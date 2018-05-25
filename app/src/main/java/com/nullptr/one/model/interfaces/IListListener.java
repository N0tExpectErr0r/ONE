package com.nullptr.one.model.interfaces;

import com.nullptr.one.bean.Article;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/24
 * @DESCRIPTION 列表Listener对应接口
 */
public interface IListListener {

    interface BaseListListener {

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    interface OnArticleListListener extends BaseListListener {

        void onSuccess(List<Article> articleList);
    }

    interface OnMovieListListener extends BaseListListener {

        void onSuccess(List<Movie> movieList);
    }

    interface OnMusicListListener extends BaseListListener {

        void onSuccess(List<Music> musicList);
    }
}
