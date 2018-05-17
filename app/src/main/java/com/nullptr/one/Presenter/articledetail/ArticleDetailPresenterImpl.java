package com.nullptr.one.Presenter.articledetail;

import com.nullptr.one.activity.ArticleDetailView;
import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.model.articledetail.ArticleDetailModel;
import com.nullptr.one.model.articledetail.ArticleDetailModelImpl;
import com.nullptr.one.model.articledetail.OnArticleDetailListener;

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
    public void getArticleDetail(String itemId) {
        mArticleDetailModel.getArticleDetail(this, itemId);
    }

    @Override
    public void onSuccess(ArticleDetail articleDetail) {
        mArticleDetailView.setArticle(articleDetail);
    }

    @Override
    public void onFail(String errorMsg) {
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
