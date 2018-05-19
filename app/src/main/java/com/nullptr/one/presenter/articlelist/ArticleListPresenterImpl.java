package com.nullptr.one.presenter.articlelist;

import com.nullptr.one.bean.Article;
import com.nullptr.one.fragment.ArticleListView;
import com.nullptr.one.model.articlelist.ArticleListModel;
import com.nullptr.one.model.articlelist.ArticleListModelImpl;
import com.nullptr.one.model.articlelist.OnArticleListListener;
import com.nullptr.one.model.articlelist.OnMoreArticleListener;
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
    public void loadArticleList() {
        mArticleListModel.getList(this);
    }

    @Override
    public void loadMore(String lastId) {
        mArticleListModel.getMore(this, lastId);
    }

    @Override
    public void onSuccess(List<Article> articleList) {
        mArticleListView.setArticleList(articleList);
    }

    @Override
    public void onMoreSuccess(List<Article> articleList) {
        mArticleListView.addArticleList(articleList);
    }

    @Override
    public void onFail(String errorMsg) {
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
