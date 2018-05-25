package com.nullptr.one.presenter.interfaces;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/22
 * @DESCRIPTION 各类详情的presenter存放的地方
 */
public interface IDetailPresenter {

    interface ArticleDetailPresenter {

        void getArticleDetail(String itemId);
    }

    interface ImageDetailPresenter {

        void getImageDetailList();
    }

    interface MovieDetailPresenter {

        void getMovieDetail(String itemId);
    }

    interface MovieInfoPresenter {

        void getMovieInfo(String itemId);
    }

    interface MusicDetailPresenter {

        void getMusicDetail(String itemId);
    }
}
