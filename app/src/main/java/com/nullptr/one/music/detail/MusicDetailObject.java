package com.nullptr.one.music.detail;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class MusicDetailObject {

    @SerializedName("data")
    private MusicDetail musicDetail;

    public MusicDetail getMusicDetail() {
        return musicDetail;
    }

    public void setMusicDetail(MusicDetail musicDetail) {
        this.musicDetail = musicDetail;
    }
}
