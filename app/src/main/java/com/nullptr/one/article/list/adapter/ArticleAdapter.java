package com.nullptr.one.article.list.adapter;

import android.content.Context;
import com.nullptr.one.R;
import com.nullptr.one.base.CommonAdapter;
import com.nullptr.one.base.ViewHolder;
import com.nullptr.one.bean.Article;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 文章列表的Adapter
 */
public class ArticleAdapter extends CommonAdapter<Article> {

    public ArticleAdapter(Context context, List<Article> datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    public void convert(ViewHolder holder, Article article) {
        holder.setImageUrl(R.id.article_iv_image, article.getImageUrl())
                .setText(R.id.article_tv_title, article.getTitle())
                .setText(R.id.article_tv_forward, article.getForward())
                .setText(R.id.article_tv_date, article.getDate())
                .setText(R.id.article_tv_like, article.getLikeCount() + "")
                .setText(R.id.article_tv_author_name, "文/" + article.getAuthor().getName())
                .setText(R.id.article_tv_author_summary, article.getAuthor().getDesc());
    }

}
