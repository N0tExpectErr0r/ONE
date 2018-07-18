package com.nullptr.one.movie.list;

import com.nullptr.one.R;
import com.nullptr.one.base.BaseAdapter;
import com.nullptr.one.base.CommonViewHolder;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION 影视列表的Adapter
 */
public class MovieAdapter extends BaseAdapter<Movie> {

    public MovieAdapter(List<Movie> datas, int layoutId, int itemCount) {
        super(datas, layoutId, itemCount);
    }

    @Override
    public void initItemView(CommonViewHolder holder, Movie movie) {
        holder.setImageUrl(R.id.movie_iv_image, movie.getImageURL())
                .setText(R.id.movie_tv_title, movie.getTitle())
                .setText(R.id.movie_tv_subtitle, movie.getSubTitle())
                .setText(R.id.movie_tv_forward, movie.getForward())
                .setText(R.id.movie_tv_date, movie.getDate())
                .setText(R.id.movie_tv_author_name, "作者：" + movie.getAuthor().getName());
    }
}
