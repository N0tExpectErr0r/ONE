package com.nullptr.one.article.detail.presenter;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailModel;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailPresenter;
import com.nullptr.one.article.detail.IArticleDetail.ArticleDetailView;
import com.nullptr.one.article.detail.IArticleDetail.OnArticleDetailListener;
import com.nullptr.one.article.detail.model.ArticleDetailModelImpl;

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
    private Handler mUiHandler;

    public ArticleDetailPresenterImpl(ArticleDetailView articleDetailView) {
        mArticleDetailView = articleDetailView;
        mArticleDetailModel = new ArticleDetailModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getArticleDetail(final String itemId) {
        mArticleDetailModel.getArticleDetail(ArticleDetailPresenterImpl.this, itemId);
    }

    @Override
    public void onSuccess(final ArticleDetail articleDetail) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.setArticle(articleDetail);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.hideLoading();
            }
        });
    }
}
