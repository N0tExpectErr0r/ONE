package com.nullptr.one.article.detail;

import android.os.Handler;
import android.os.Looper;
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
    private Handler mHandler;

    public ArticleDetailPresenterImpl(ArticleDetailView articleDetailView) {
        mArticleDetailView = articleDetailView;
        mArticleDetailModel = new ArticleDetailModelImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getArticleDetail(final String itemId) {
        mArticleDetailModel.getArticleDetail(ArticleDetailPresenterImpl.this, itemId);
    }

    @Override
    public void onSuccess(final ArticleDetail articleDetail) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.setArticle(articleDetail);
            }
        });

    }

    @Override
    public void onFail(final String errorMsg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.showError(errorMsg);
            }
        });

    }

    @Override
    public void onStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleDetailView.hideLoading();
            }
        });
    }
}
