package com.nullptr.one.model.musicdetail;

import com.nullptr.one.bean.MusicDetail;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicDetailModel接口实现类
 */
public class MusicDetailModelImpl implements MusicDetailModel {

    @Override
    public void getMusicDetail(final OnMusicDetailListener onMusicDetailListener, String itemId) {
        HttpUtil.sendHttpRequest("getMusicDetail", itemId, new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                MusicDetail musicDetail = JsonUtil.parseJsonToMusicDetail(response);
                onMusicDetailListener.onSuccess(musicDetail);
            }

            @Override
            public void onError(String errorMsg) {
                onMusicDetailListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMusicDetailListener.onStart();
            }

            @Override
            public void onFinish() {
                onMusicDetailListener.onFinish();
            }
        });
    }
}
