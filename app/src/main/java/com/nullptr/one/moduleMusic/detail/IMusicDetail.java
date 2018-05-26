package com.nullptr.one.moduleMusic.detail;

import com.nullptr.one.bean.MusicDetail;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IMusicDetail {

    //Model层
    interface MusicDetailModel {

        void getMusicDetail(OnMusicDetailListener onMusicDetailListener, String itemId);
    }

    interface OnMusicDetailListener {

        void onSuccess(MusicDetail musicDetail);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    //Presenter层
    interface MusicDetailPresenter {

        void getMusicDetail(String itemId);
    }

    //View层
    interface MusicDetailView {

        void setMusic(MusicDetail music);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }
}
