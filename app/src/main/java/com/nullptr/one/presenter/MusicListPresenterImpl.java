package com.nullptr.one.presenter;

import com.nullptr.one.bean.Music;
import com.nullptr.one.model.MusicListModelImpl;
import com.nullptr.one.model.interfaces.IListListener.OnMusicListListener;
import com.nullptr.one.model.interfaces.IListModel.MusicListModel;
import com.nullptr.one.model.interfaces.IListMoreListener.OnMoreMusicListener;
import com.nullptr.one.presenter.interfaces.IListPresenter;
import com.nullptr.one.view.interfaces.IListView.MusicListView;
import java.util.List;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicPresenter实现类
 */
public class MusicListPresenterImpl implements IListPresenter, OnMusicListListener,
        OnMoreMusicListener {

    private MusicListView mMusicListView;
    private MusicListModel mMusicListModel;

    public MusicListPresenterImpl(MusicListView musicListView) {
        mMusicListView = musicListView;
        mMusicListModel = new MusicListModelImpl();
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
