package com.nullptr.one.presenter;

import com.nullptr.one.bean.Article;
import com.nullptr.one.model.ArticleListModelImpl;
import com.nullptr.one.model.interfaces.IListListener.OnArticleListListener;
import com.nullptr.one.model.interfaces.IListModel.ArticleListModel;
import com.nullptr.one.model.interfaces.IListMoreListener.OnMoreArticleListener;
import com.nullptr.one.presenter.interfaces.IListPresenter;
import com.nullptr.one.view.interfaces.IListView.ArticleListView;
import java.util.List;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticlePresenter实现类
 */
public class ArticleListPresenterImpl implements IListPresenter, OnArticleListListener,
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
