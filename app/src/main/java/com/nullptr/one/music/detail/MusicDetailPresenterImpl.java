package com.nullptr.one.music.detail;

import android.os.Handler;
import android.os.Looper;
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
    private Handler mHandler;

    public MusicDetailPresenterImpl(MusicDetailView musicDetailView) {
        mMusicDetailView = musicDetailView;
        mMusicDetailModel = new MusicDetailModelImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getMusicDetail(final String itemId) {
        mMusicDetailModel.getMusicDetail(this, itemId);
    }

    @Override
    public void onSuccess(final MusicDetail musicDetail) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.setMusic(musicDetail);
            }
        });

    }

    @Override
    public void onFail(final String errorMsg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.showError(errorMsg);
            }
        });

    }

    @Override
    public void onStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicDetailView.hideLoading();
            }
        });
    }
}
