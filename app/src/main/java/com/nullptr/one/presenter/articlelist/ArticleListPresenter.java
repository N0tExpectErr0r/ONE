package com.nullptr.one.presenter.articlelist;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 文章相关Presenter
 */
public interface ArticleListPresenter {

    void loadArticleList();

    void loadMore(String lastId);
}
