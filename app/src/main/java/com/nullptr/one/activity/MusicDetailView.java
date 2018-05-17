package com.nullptr.one.activity;

import com.nullptr.one.bean.MusicDetail;

/**
 * View层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicDetailView接口，用于对Presenter层的结果做出反馈
 */
public interface MusicDetailView {

    void setMusic(MusicDetail music);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();
}
