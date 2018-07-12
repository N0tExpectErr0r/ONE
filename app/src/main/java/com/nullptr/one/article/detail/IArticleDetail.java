package com.nullptr.one.article.detail;

import com.nullptr.one.bean.ArticleDetail;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IArticleDetail {

    //Model层
    interface ArticleDetailModel {

        void getArticleDetail(OnArticleDetailListener onArticleDetailListener, String itemId);
    }

    interface OnArticleDetailListener {

        void onSuccess(ArticleDetail articleDetail);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    //Presenter层
    interface ArticleDetailPresenter {

        void getArticleDetail(String itemId);
    }

    //View层
    interface ArticleDetailView {

        void setArticle(ArticleDetail article);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }

}
