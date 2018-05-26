package com.nullptr.one.moduleMusic.list.presenter;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.Music;
import com.nullptr.one.moduleMusic.list.IMusicList.MusicListModel;
import com.nullptr.one.moduleMusic.list.IMusicList.MusicListPresenter;
import com.nullptr.one.moduleMusic.list.IMusicList.MusicListView;
import com.nullptr.one.moduleMusic.list.IMusicList.OnMoreMusicListener;
import com.nullptr.one.moduleMusic.list.IMusicList.OnMusicListListener;
import com.nullptr.one.moduleMusic.list.model.MusicListModelImpl;
import java.util.List;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicPresenter实现类
 */
public class MusicListPresenterImpl implements MusicListPresenter, OnMusicListListener,
        OnMoreMusicListener {

    private MusicListView mMusicListView;
    private MusicListModel mMusicListModel;
    private Handler mUiHandler;

    public MusicListPresenterImpl(MusicListView musicListView) {
        mMusicListView = musicListView;
        mMusicListModel = new MusicListModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void loadList() {
        mMusicListModel.getList(this);
    }

    @Override
    public void loadMore(String lastId) {
        mMusicListModel.getMore(this, lastId);
    }

    @Override
    public void onSuccess(final List<Music> musicList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicListView.setMusicList(musicList);
            }
        });

    }

    @Override
    public void onMoreSuccess(final List<Music> musicList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicListView.addMusicList(musicList);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicListView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicListView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mMusicListView.hideLoading();
            }
        });
    }
}
