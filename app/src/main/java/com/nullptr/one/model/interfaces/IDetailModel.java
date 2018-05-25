package com.nullptr.one.model.interfaces;

import com.nullptr.one.model.interfaces.IDetailListener.OnArticleDetailListener;
import com.nullptr.one.model.interfaces.IDetailListener.OnImageDetailListener;
import com.nullptr.one.model.interfaces.IDetailListener.OnMovieDetailListener;
import com.nullptr.one.model.interfaces.IDetailListener.OnMovieInfoListener;
import com.nullptr.one.model.interfaces.IDetailListener.OnMusicDetailListener;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/24
 * @DESCRIPTION 详情Model对应接口
 */
public interface IDetailModel {

    interface ArticleDetailModel {

        void getArticleDetail(OnArticleDetailListener onArticleDetailListener, String itemId);
    }

    interface MovieDetailModel {

        void getMovieDetail(OnMovieDetailListener onMovieDetailListener, String itemId);
    }

    interface MovieInfoModel {

        void getMovieInfo(OnMovieInfoListener onMovieInfoListener, String itemId);
    }

    interface MusicDetailModel {

        void getMusicDetail(OnMusicDetailListener onMusicDetailListener, String itemId);
    }

    interface ImageDetailModel {

        void getImageDetailList(OnImageDetailListener onImageDetailListener);
    }
}
