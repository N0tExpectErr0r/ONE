package com.nullptr.one.adapter;

import android.content.Context;
import com.nullptr.one.R;
import com.nullptr.one.bean.Movie;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION 影视列表的Adapter
 */
public class MovieAdapter extends CommonAdapter<Movie> {

    public MovieAdapter(Context context, List<Movie> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Movie movie) {
        holder.setImageBitmap(R.id.movie_iv_image, movie.getImage())
                .setText(R.id.movie_tv_title, movie.getTitle())
                .setText(R.id.movie_tv_subtitle, movie.getSubTitle())
                .setText(R.id.movie_tv_forward, movie.getForward())
                .setText(R.id.movie_tv_date, movie.getDate())
                .setText(R.id.movie_tv_author_name, "作者"+movie.getAuthor().getName());
    }
}
