package com.nullptr.one.article.list;

import android.os.Handler;
import android.os.Looper;
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
    private Handler mHandler;

    public ArticleListPresenterImpl(ArticleListView articleListView) {
        mArticleListView = articleListView;
        mArticleListModel = new ArticleListModelImpl();
        mHandler = new Handler(Looper.getMainLooper());
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
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.setArticleList(articleList);
            }
        });

    }

    @Override
    public void onMoreSuccess(final List<Article> articleList) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.addArticleList(articleList);
            }
        });

    }

    @Override
    public void onFail(final String errorMsg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.showError(errorMsg);
            }
        });


    }

    @Override
    public void onStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.showLoading();
            }
        });

    }

    @Override
    public void onFinish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mArticleListView.hideLoading();
            }
        });

    }
}
