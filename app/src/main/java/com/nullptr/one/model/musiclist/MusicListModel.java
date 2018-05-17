package com.nullptr.one.model.musiclist;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicModel对应接口
 */
public interface MusicListModel {

    void getList(OnMusicListListener listener);

    void getMore(OnMoreMusicListener onMoreMusicListener, String lastId);
}
