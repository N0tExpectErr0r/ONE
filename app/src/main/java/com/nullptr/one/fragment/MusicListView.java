package com.nullptr.one.fragment;

import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * View层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicListView接口，用于对Presenter层的结果做出反馈
 */
public interface MusicListView {

    void setMusicList(List<Music> musicList);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();

    void addMusicList(List<Music> musicList);
}