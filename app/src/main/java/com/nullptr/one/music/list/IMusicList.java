package com.nullptr.one.music.list;

import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IMusicList {
    //Model层

    interface MusicListModel {

        void getList(OnMusicListListener listener);

        void getListFromNet(OnMusicListListener listener);

        void getMore(OnMoreMusicListener onMoreMusicListener, String lastId);
    }

    interface OnMusicListListener {

        void onSuccess(List<Music> musicList);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    interface OnMoreMusicListener {

        void onMoreSuccess(List<Music> musicList);

        void onFail(String errorMsg);
    }

    //Presenter层
    interface MusicListPresenter {

        void loadList();

        void updateList();

        void loadMore(String lastId);
    }

    //View层
    interface MusicListView {

        void setMusicList(List<Music> musicList);

        void addMusicList(List<Music> musicList);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }
}
