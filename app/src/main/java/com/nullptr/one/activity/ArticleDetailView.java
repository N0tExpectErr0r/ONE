package com.nullptr.one.activity;

import com.nullptr.one.bean.ArticleDetail;

/**
 * View层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArtiicleDetailView接口，用于对Presenter层的结果做出反馈
 */
public interface ArticleDetailView {

    void setArticle(ArticleDetail article);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();
}
