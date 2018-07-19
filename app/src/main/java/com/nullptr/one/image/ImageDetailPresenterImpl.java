package com.nullptr.one.image;

import com.nullptr.one.image.IImageDetail.ImageDetailModel;
import com.nullptr.one.image.IImageDetail.ImageDetailPresenter;
import com.nullptr.one.image.IImageDetail.ImageDetailView;
import com.nullptr.one.image.IImageDetail.OnImageDetailListener;
import java.util.List;

/**
 * Presenter层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION ImageDetailPresenter实现类
 */
public class ImageDetailPresenterImpl implements ImageDetailPresenter, OnImageDetailListener {

    private ImageDetailView mImageDetailView;
    private ImageDetailModel mImageDetailModel;

    ImageDetailPresenterImpl(ImageDetailView imageDetailView) {
        mImageDetailView = imageDetailView;
        mImageDetailModel = new ImageDetailModelImpl();
    }


    @Override
    public void getImageDetailList() {
        mImageDetailModel.getImageDetailList(ImageDetailPresenterImpl.this);
    }

    @Override
    public void onSuccess(final List<ImageDetail> imageDetailList) {
        mImageDetailView.setImageDetailList(imageDetailList);
    }

    @Override
    public void onFail(final String errorMsg) {
        mImageDetailView.showError(errorMsg);
    }

    @Override
    public void onStart() {
        mImageDetailView.showLoading();
    }

    @Override
    public void onFinish() {

        mImageDetailView.hideLoading();
    }
}
