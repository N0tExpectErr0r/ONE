package com.nullptr.one.presenter;

import com.nullptr.one.bean.MusicDetail;
import com.nullptr.one.model.musicdetail.MusicDetailModel;
import com.nullptr.one.model.musicdetail.MusicDetailModelImpl;
import com.nullptr.one.model.musicdetail.OnMusicDetailListener;
import com.nullptr.one.presenter.interfaces.DetailPresenter.MusicDetailPresenter;
import com.nullptr.one.view.interfaces.IDetailView.MusicDetailView;

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
    public void onSuccess(MusicDetail musicDetail) {
        mMusicDetailView.setMusic(musicDetail);
    }

    @Override
    public void onFail(String errorMsg) {
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
