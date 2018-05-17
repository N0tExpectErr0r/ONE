package com.nullptr.one.model.musicdetail;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicDetail对应接口
 */
public interface MusicDetailModel {

    void getMusicDetail(OnMusicDetailListener onMusicDetailListener, String itemId);
}
