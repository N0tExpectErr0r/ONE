package com.nullptr.one.model.interfaces;

import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.bean.MusicDetail;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/24
 * @DESCRIPTION 详情Listener对应接口
 */
public interface IDetailListener {

    interface BaseDetailListener {

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    interface OnArticleDetailListener extends BaseDetailListener {

        void onSuccess(ArticleDetail articleDetail);
    }

    interface OnMusicDetailListener extends BaseDetailListener {

        void onSuccess(MusicDetail musicDetail);
    }

    interface OnMovieDetailListener extends BaseDetailListener {

        void onSuccess(MovieDetail movieDetail);
    }

    interface OnMovieInfoListener extends BaseDetailListener {

        void onSuccess(MovieInfo movieInfo);
    }

    interface OnImageDetailListener extends BaseDetailListener {

        void onSuccess(List<ImageDetail> imageList);
    }

}
