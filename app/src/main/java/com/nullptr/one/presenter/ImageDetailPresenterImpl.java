package com.nullptr.one.presenter;

import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.model.ImageDetailModelImpl;
import com.nullptr.one.model.interfaces.IDetailListener.OnImageDetailListener;
import com.nullptr.one.model.interfaces.IDetailModel.ImageDetailModel;
import com.nullptr.one.presenter.interfaces.IDetailPresenter.ImageDetailPresenter;
import com.nullptr.one.view.interfaces.IDetailView.ImageDetailView;
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

    public ImageDetailPresenterImpl(ImageDetailView imageDetailView) {
        mImageDetailView = imageDetailView;
        mImageDetailModel = new ImageDetailModelImpl();
    }


    @Override
    public void getImageDetailList() {
        mImageDetailModel.getImageDetailList(this);
    }

    @Override
    public void onSuccess(List<ImageDetail> imageDetailList) {
        mImageDetailView.setImageDetailList(imageDetailList);
    }

    @Override
    public void onFail(String errorMsg) {
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
