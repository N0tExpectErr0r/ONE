package com.nullptr.one.model.musiclist;

import com.nullptr.one.bean.Music;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicModel实现类
 */
public class MusicListModelImpl implements MusicListModel {

    @Override
    public void getList(final OnMusicListListener onMusicListListener) {
        HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/channel/music/more/{id}", "0", new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Music> musicList = JsonUtil.parseJsonToMusicList(response);
                onMusicListListener.onSuccess(musicList);
            }

            @Override
            public void onError(String errorMsg) {
                onMusicListListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMusicListListener.onStart();
            }

            @Override
            public void onFinish() {
                onMusicListListener.onFinish();
            }
        });
    }

    @Override
    public void getMore(final OnMoreMusicListener onMoreMusicListener, String lastId) {
        HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/channel/music/more/{id}", lastId, new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Music> musicList = JsonUtil.parseJsonToMusicList(response);
                onMoreMusicListener.onMoreSuccess(musicList);
            }

            @Override
            public void onError(String errorMsg) {
                onMoreMusicListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }
        });
    }
}
