package com.nullptr.one.article.list.presenter;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.Article;
import com.nullptr.one.article.list.IArticleList.ArticleListModel;
import com.nullptr.one.article.list.IArticleList.ArticleListPresenter;
import com.nullptr.one.article.list.IArticleList.ArticleListView;
import com.nullptr.one.article.list.IArticleList.OnArticleListListener;
import com.nullptr.one.article.list.IArticleList.OnMoreArticleListener;
import com.nullptr.one.article.list.model.ArticleListModelImpl;
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
    private Handler mUiHandler;

    public ArticleListPresenterImpl(ArticleListView articleListView) {
        mArticleListView = articleListView;
        mArticleListModel = new ArticleListModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
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
    public void onSuccess(final List<Article> articleList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.setArticleList(articleList);
            }
        });
    }

    @Override
    public void onMoreSuccess(final List<Article> articleList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.addArticleList(articleList);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.hideLoading();
            }
        });
    }
}
