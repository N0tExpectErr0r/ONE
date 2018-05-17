package com.nullptr.one.adapter;

import android.content.Context;
import com.nullptr.one.R;
import com.nullptr.one.bean.Music;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION
 */
public class MusicAdapter extends CommonAdapter<Music> {

    public MusicAdapter(Context context, List<Music> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Music music) {
        holder.setImageBitmap(R.id.music_civ_image, music.getImage())
                .setText(R.id.music_tv_title, music.getTitle())
                .setText(R.id.music_tv_forward, music.getForward())
                .setText(R.id.music_tv_singer_name, "歌手:" + music.getSinger().getName())
                .setText(R.id.music_tv_singer_desc, music.getSinger().getDesc());
    }
}
