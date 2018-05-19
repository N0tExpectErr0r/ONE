package com.nullptr.one.presenter.musiclist;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐相关Presenter
 */
public interface MusicListPresenter {

    void loadMusicList();

    void loadMore(String lastId);
}
