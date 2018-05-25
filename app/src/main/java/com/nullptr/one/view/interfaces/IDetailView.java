package com.nullptr.one.view.interfaces;

import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.bean.MusicDetail;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/22
 * @DESCRIPTION 各类详情view对应接口
 */
public interface IDetailView {

    interface BaseDetailView {

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }

    interface ArticleDetailView extends BaseDetailView {

        void setArticle(ArticleDetail article);
    }

    interface ImageDetailView extends BaseDetailView {

        void setImageDetailList(List<ImageDetail> imageDetailList);
    }

    interface MovieDetailView extends BaseDetailView {

        void setMovieDetail(MovieDetail musicDetail);

        void setMovieInfo(MovieInfo musicInfo);
    }

    interface MusicDetailView extends BaseDetailView {

        void setMusic(MusicDetail music);

    }


}
