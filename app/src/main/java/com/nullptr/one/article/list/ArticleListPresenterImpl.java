package com.nullptr.one.article.list;

import com.nullptr.one.article.list.IArticleList.ArticleListModel;
import com.nullptr.one.article.list.IArticleList.ArticleListPresenter;
import com.nullptr.one.article.list.IArticleList.ArticleListView;
import com.nullptr.one.article.list.IArticleList.OnArticleListListener;
import com.nullptr.one.article.list.IArticleList.OnMoreArticleListener;
import java.util.List;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticlePresenter实现类
 */
public class ArticleListPresenterImpl implements ArticleListPresenter, OnArticleListListener,
        OnMoreArticleListener {

    private ArticleListView mArticleListView;
    private ArticleListModel mArticleListModel;

    public ArticleListPresenterImpl(ArticleListView articleListView) {
        mArticleListView = articleListView;
        mArticleListModel = new ArticleListModelImpl();
    }

    @Override
    public void loadList() {
        mArticleListModel.getList(this);
    }

    @Override
    public void updateList() {
        mArticleListModel.getListFromNet(this);
    }

    @Override
    public void loadMore(String lastId) {
        mArticleListModel.getMore(this, lastId);
    }

    @Override
    public void onSuccess(final List<Article> articleList) {
        mArticleListView.setArticleList(articleList);
    }

    @Override
    public void onMoreSuccess(final List<Article> articleList) {
        mArticleListView.addArticleList(articleList);
    }

    @Override
    public void onFail(final String errorMsg) {
        mArticleListView.showError(errorMsg);

    }

    @Override
    public void onStart() {
        mArticleListView.showLoading();
    }

    @Override
    public void onFinish() {
        mArticleListView.hideLoading();
    }
}
