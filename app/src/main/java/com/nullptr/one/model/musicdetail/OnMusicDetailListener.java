package com.nullptr.one.model.musicdetail;

import com.nullptr.one.bean.MusicDetail;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnMusicDetailListener {

    void onSuccess(MusicDetail musicDetail);

    void onFail(String errorMsg);

    void onStart();

    void onFinish();
}
