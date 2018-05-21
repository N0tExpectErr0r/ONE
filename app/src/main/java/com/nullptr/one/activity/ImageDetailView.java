package com.nullptr.one.activity;

import com.nullptr.one.bean.ImageDetail;
import java.util.List;

/**
 * View层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION
 */
public interface ImageDetailView {
    void setImageDetailList(List<ImageDetail> imageDetailList);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();
}
