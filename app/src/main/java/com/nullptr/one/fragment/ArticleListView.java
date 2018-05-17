package com.nullptr.one.fragment;

import com.nullptr.one.bean.Article;
import java.util.List;

/**
 * View层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleListView接口，用于对Presenter层的结果做出反馈
 */
public interface ArticleListView {

    void setArticleList(List<Article> articleList);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();

    void addArticleList(List<Article> articleList);
}
