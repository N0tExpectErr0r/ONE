package com.nullptr.one.moduleMusic.detail.presenter;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.MusicDetail;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.MusicDetailModel;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.MusicDetailPresenter;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.MusicDetailView;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.OnMusicDetailListener;
import com.nullptr.one.moduleMusic.detail.model.MusicDetailModelImpl;

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
    private Handler mUiHandler;

    public MusicDetailPresenterImpl(MusicDetailView musicDetailView) {
        mMusicDetailView = musicDetailView;
        mMusicDetailModel = new MusicDetailModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getMusicDetail(String itemId) {
        mMusicDetailModel.getMusicDetail(this, itemId);
    }

    @Override
    public void onSuccess(final MusicDetail musicDetail) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.setMusic(musicDetail);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.hideLoading();
            }
        });
    }
}
