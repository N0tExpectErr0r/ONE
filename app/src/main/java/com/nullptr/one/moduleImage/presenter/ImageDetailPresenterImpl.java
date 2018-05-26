package com.nullptr.one.moduleImage.presenter;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.moduleImage.IImageDetail.ImageDetailModel;
import com.nullptr.one.moduleImage.IImageDetail.ImageDetailPresenter;
import com.nullptr.one.moduleImage.IImageDetail.ImageDetailView;
import com.nullptr.one.moduleImage.IImageDetail.OnImageDetailListener;
import com.nullptr.one.moduleImage.model.ImageDetailModelImpl;
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
    private Handler mUiHandler;

    public ImageDetailPresenterImpl(ImageDetailView imageDetailView) {
        mImageDetailView = imageDetailView;
        mImageDetailModel = new ImageDetailModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getImageDetailList() {
        mImageDetailModel.getImageDetailList(ImageDetailPresenterImpl.this);
    }

    @Override
    public void onSuccess(final List<ImageDetail> imageDetailList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mImageDetailView.setImageDetailList(imageDetailList);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mImageDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mImageDetailView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mImageDetailView.hideLoading();
            }
        });
    }
}
