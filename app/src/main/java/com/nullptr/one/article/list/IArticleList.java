package com.nullptr.one.article.list;

import com.nullptr.one.bean.Article;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IArticleList {

    //Model层
    interface ArticleListModel {

        void getList(OnArticleListListener listener);

        void getMore(OnMoreArticleListener onMoreArticleListener, String lastId);
    }

    interface OnArticleListListener {

        void onSuccess(List<Article> articleList);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    interface OnMoreArticleListener {

        void onMoreSuccess(List<Article> articleList);

        void onFail(String errorMsg);
    }

    //Presenter层
    interface ArticleListPresenter {

        void loadList();

        void loadMore(String lastId);
    }

    //View层
    interface ArticleListView {

        void setArticleList(List<Article> articleList);

        void addArticleList(List<Article> articleList);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }

}
