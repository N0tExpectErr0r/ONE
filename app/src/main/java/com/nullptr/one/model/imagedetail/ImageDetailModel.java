package com.nullptr.one.model.imagedetail;

import com.nullptr.one.model.imagedetail.OnImageDetailListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION ImageDetail对应接口
 */
public interface ImageDetailModel {

    void getImageDetailList(OnImageDetailListener onImageDetailListener);
}
