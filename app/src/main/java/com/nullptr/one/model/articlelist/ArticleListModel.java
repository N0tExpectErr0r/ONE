package com.nullptr.one.model.articlelist;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleModel对应接口
 */
public interface ArticleListModel {

    void getList(OnArticleListListener listener);

    void getMore(OnMoreArticleListener onMoreArticleListener, String lastId);
}
