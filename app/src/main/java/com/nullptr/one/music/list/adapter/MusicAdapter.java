package com.nullptr.one.music.list.adapter;

import android.content.Context;
import com.nullptr.one.R;
import com.nullptr.one.base.CommonAdapter;
import com.nullptr.one.base.ViewHolder;
import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐列表Adapter
 */
public class MusicAdapter extends CommonAdapter<Music> {

    public MusicAdapter(Context context, List<Music> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Music music) {
        holder.setImageUrl(R.id.music_civ_image, music.getImageURL())
                .setText(R.id.music_tv_title, music.getTitle())
                .setText(R.id.music_tv_forward, music.getForward())
                .setText(R.id.music_tv_singer_name, "歌手:" + music.getSinger().getName())
                .setText(R.id.music_tv_singer_desc, music.getSinger().getDesc());
    }
}
