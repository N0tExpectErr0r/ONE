package com.nullptr.one.model.interfaces;

import com.nullptr.one.bean.Article;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/24
 * @DESCRIPTION 加载更多列表Listener对应接口
 */
public interface IListMoreListener {

    interface BaseListMoreListener {

        void onFail(String errorMsg);
    }

    interface OnMoreArticleListener extends BaseListMoreListener {

        void onMoreSuccess(List<Article> articleList);
    }

    interface OnMoreMovieListener extends BaseListMoreListener {

        void onMoreSuccess(List<Movie> movieList);
    }

    interface OnMoreMusicListener extends BaseListMoreListener {

        void onMoreSuccess(List<Music> musicList);
    }
}

