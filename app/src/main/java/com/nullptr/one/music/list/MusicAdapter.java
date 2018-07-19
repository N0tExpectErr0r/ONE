package com.nullptr.one.music.list;

import com.nullptr.one.R;
import com.nullptr.one.base.BaseAdapter;
import com.nullptr.one.base.CommonViewHolder;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐列表Adapter
 */
public class MusicAdapter extends BaseAdapter<Music> {

    MusicAdapter(List<Music> datas, int layoutId, int itemCount) {
        super(datas, layoutId, itemCount);
    }

    @Override
    public void initItemView(CommonViewHolder holder, Music music) {
        holder.setImageUrl(R.id.music_civ_image, music.getImageURL())
                .setText(R.id.music_tv_title, music.getTitle())
                .setText(R.id.music_tv_forward, music.getForward())
                .setText(R.id.music_tv_singer_name, "歌手:" + music.getSinger().getName())
                .setText(R.id.music_tv_singer_desc, music.getSinger().getDesc());
    }
}
