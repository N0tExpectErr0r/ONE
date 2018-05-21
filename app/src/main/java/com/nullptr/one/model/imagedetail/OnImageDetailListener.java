package com.nullptr.one.model.imagedetail;

import com.nullptr.one.bean.ImageDetail;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnImageDetailListener {

    void onSuccess(List<ImageDetail> imageList);

    void onFail(String errorMsg);

    void onStart();

    void onFinish();
}
