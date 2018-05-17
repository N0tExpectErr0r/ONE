package com.nullptr.one.model.articledetail;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleDetail对应接口
 */
public interface ArticleDetailModel {

    void getArticleDetail(OnArticleDetailListener onArticleDetailListener, String itemId);
}
