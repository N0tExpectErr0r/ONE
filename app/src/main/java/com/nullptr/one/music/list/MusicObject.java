package com.nullptr.one.music.list;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class MusicObject {

    @SerializedName("data")
    private java.util.List<Music> musicList;

    public List<Music> getMusicList() {
        return musicList;
    }

}
