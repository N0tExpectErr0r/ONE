package com.nullptr.one.music.detail;

import com.nullptr.one.music.detail.IMusicDetail.MusicDetailModel;
import com.nullptr.one.music.detail.IMusicDetail.MusicDetailPresenter;
import com.nullptr.one.music.detail.IMusicDetail.MusicDetailView;
import com.nullptr.one.music.detail.IMusicDetail.OnMusicDetailListener;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicDetailPresenter实现类
 */
public class MusicDetailPresenterImpl implements MusicDetailPresenter, OnMusicDetailListener {

    private MusicDetailView mMusicDetailView;
    private MusicDetailModel mMusicDetailModel;

    public MusicDetailPresenterImpl(MusicDetailView musicDetailView) {
        mMusicDetailView = musicDetailView;
        mMusicDetailModel = new MusicDetailModelImpl();
    }


    @Override
    public void getMusicDetail(String itemId) {
        mMusicDetailModel.getMusicDetail(this, itemId);
    }

    @Override
    public void onSuccess(final MusicDetail musicDetail) {
        mMusicDetailView.setMusic(musicDetail);
    }

    @Override
    public void onFail(final String errorMsg) {
        mMusicDetailView.showError(errorMsg);
    }

    @Override
    public void onStart() {
        mMusicDetailView.showLoading();
    }

    @Override
    public void onFinish() {
        mMusicDetailView.hideLoading();
    }
}
