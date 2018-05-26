package com.nullptr.one.moduleMusic.list.model;

import com.nullptr.one.bean.Music;
import com.nullptr.one.moduleMusic.list.IMusicList.MusicListModel;
import com.nullptr.one.moduleMusic.list.IMusicList.OnMoreMusicListener;
import com.nullptr.one.moduleMusic.list.IMusicList.OnMusicListListener;
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
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/music/more/")
                .append("0")
                .append("?platform=android");
        HttpUtil.sendHttpRequest(url.toString(), new OnRequestListener() {
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
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/music/more/")
                .append(lastId)
                .append("?platform=android");
        HttpUtil.sendHttpRequest(url.toString(), new OnRequestListener() {
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
