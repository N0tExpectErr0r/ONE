package com.nullptr.one.article.detail;

import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailModel;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailPresenter;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailView;
import com.nullptr.one.article.detail.IArticleDetail.OnArticleDetailListener;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleDetailPresenter实现类
 */
public class ArticleDetailPresenterImpl implements ArticleDetailPresenter, OnArticleDetailListener {

    private ArticleDetailView mArticleDetailView;
    private ArticleDetailModel mArticleDetailModel;

    public ArticleDetailPresenterImpl(ArticleDetailView articleDetailView) {
        mArticleDetailView = articleDetailView;
        mArticleDetailModel = new ArticleDetailModelImpl();
    }


    @Override
    public void getArticleDetail(final String itemId) {
        mArticleDetailModel.getArticleDetail(ArticleDetailPresenterImpl.this, itemId);
    }

    @Override
    public void onSuccess(final ArticleDetail articleDetail) {
        mArticleDetailView.setArticle(articleDetail);
    }

    @Override
    public void onFail(final String errorMsg) {
        mArticleDetailView.showError(errorMsg);
    }

    @Override
    public void onStart() {
        mArticleDetailView.showLoading();
    }

    @Override
    public void onFinish() {
        mArticleDetailView.hideLoading();
    }
}
