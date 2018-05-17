package com.nullptr.one.model.movielist;

import com.nullptr.one.model.musiclist.OnMoreMusicListener;
import com.nullptr.one.model.musiclist.OnMusicListListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION MovieModel对应接口
 */
public interface MovieListModel {

    void getList(OnMovieListListener listener);

    void getMore(OnMoreMovieListener onMoreMovieListener, String lastId);
}
