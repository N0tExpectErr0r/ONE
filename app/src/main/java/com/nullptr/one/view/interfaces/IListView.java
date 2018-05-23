package com.nullptr.one.view.interfaces;

import com.nullptr.one.bean.Article;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/22
 * @DESCRIPTION 各类列表view对应接口
 */
public interface IListView {
    interface BaseListView{
        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }

    interface ArticleListView extends BaseListView{

        void setArticleList(List<Article> articleList);

        void addArticleList(List<Article> articleList);
    }

    interface MovieListView extends BaseListView{

        void setMovieList(List<Movie> movieList);

        void addMovieList(List<Movie> movieList);
    }

    interface MusicListView extends BaseListView{

        void setMusicList(List<Music> musicList);

        void addMusicList(List<Music> musicList);
    }
}
