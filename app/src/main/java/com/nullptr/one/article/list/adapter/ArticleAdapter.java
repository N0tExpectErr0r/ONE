package com.nullptr.one.article.list.adapter;

import com.nullptr.one.R;

import com.nullptr.one.base.BaseAdapter;
import com.nullptr.one.base.CommonViewHolder;
import com.nullptr.one.bean.Article;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 文章列表的Adapter
 */
public class ArticleAdapter extends BaseAdapter<Article> {
    public ArticleAdapter(List<Article> data, int layoutId,int itemCount) {
        super(data, layoutId, itemCount);
    }


    @Override
    public void initItemView(CommonViewHolder holder, Article article) {
        holder.setImageUrl(R.id.article_iv_image, article.getImageUrl())
                .setText(R.id.article_tv_title, article.getTitle())
                .setText(R.id.article_tv_forward, article.getForward())
                .setText(R.id.article_tv_date, article.getDate())
                .setText(R.id.article_tv_like, article.getLikeCount() + "")
                .setText(R.id.article_tv_author_name, "文/" + article.getAuthor().getName())
                .setText(R.id.article_tv_author_summary, article.getAuthor().getDesc());
    }

}
