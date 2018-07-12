package com.nullptr.one.image;

import com.nullptr.one.bean.ImageDetail;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IImageDetail {

    //Model层
    interface ImageDetailModel {

        void getImageDetailList(OnImageDetailListener onImageDetailListener);
    }

    interface OnImageDetailListener {

        void onSuccess(List<ImageDetail> imageList);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    //Pesenter层
    interface ImageDetailPresenter {

        void getImageDetailList();
    }

    //View层
    interface ImageDetailView {

        void setImageDetailList(List<ImageDetail> imageDetailList);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }

}
