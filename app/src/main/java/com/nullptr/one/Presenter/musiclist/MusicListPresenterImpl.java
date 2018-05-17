package com.nullptr.one.Presenter.musiclist;

import com.nullptr.one.bean.Music;
import com.nullptr.one.fragment.MusicListView;
import com.nullptr.one.model.musiclist.MusicListModel;
import com.nullptr.one.model.musiclist.MusicListModelImpl;
import com.nullptr.one.model.musiclist.OnMoreMusicListener;
import com.nullptr.one.model.musiclist.OnMusicListListener;
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

    public MusicListPresenterImpl(MusicListView musicListView) {
        mMusicListView = musicListView;
        mMusicListModel = new MusicListModelImpl();
    }

    @Override
    public void loadMusicList() {
        mMusicListModel.getList(this);
    }

    @Override
    public void loadMore(String lastId) {
        mMusicListModel.getMore(this, lastId);
    }

    @Override
    public void onSuccess(List<Music> musicList) {
        mMusicListView.setMusicList(musicList);
    }

    @Override
    public void onMoreSuccess(List<Music> musicList) {
        mMusicListView.addMusicList(musicList);
    }

    @Override
    public void onFail(String errorMsg) {
        mMusicListView.showError(errorMsg);
    }

    @Override
    public void onStart() {
        mMusicListView.showLoading();
    }

    @Override
    public void onFinish() {
        mMusicListView.hideLoading();
    }
}
