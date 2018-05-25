package com.nullptr.one.model.interfaces;


import com.nullptr.one.model.interfaces.IListListener.OnArticleListListener;
import com.nullptr.one.model.interfaces.IListListener.OnMovieListListener;
import com.nullptr.one.model.interfaces.IListListener.OnMusicListListener;
import com.nullptr.one.model.interfaces.IListMoreListener.OnMoreArticleListener;
import com.nullptr.one.model.interfaces.IListMoreListener.OnMoreMovieListener;
import com.nullptr.one.model.interfaces.IListMoreListener.OnMoreMusicListener;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/24
 * @DESCRIPTION 列表Model对应接口
 */
public interface IListModel {

    interface ArticleListModel {

        void getList(OnArticleListListener listener);

        void getMore(OnMoreArticleListener onMoreArticleListener, String lastId);
    }

    interface MovieListModel {

        void getList(OnMovieListListener listener);

        void getMore(OnMoreMovieListener onMoreMovieListener, String lastId);
    }

    interface MusicListModel {

        void getList(OnMusicListListener listener);

        void getMore(OnMoreMusicListener onMoreMusicListener, String lastId);
    }
}
