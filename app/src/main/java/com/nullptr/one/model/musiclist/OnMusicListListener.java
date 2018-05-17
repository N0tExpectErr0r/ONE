package com.nullptr.one.model.musiclist;

import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnMusicListListener {

    void onSuccess(List<Music> musicList);

    void onFail(String errorMsg);

    void onStart();

    void onFinish();
}
