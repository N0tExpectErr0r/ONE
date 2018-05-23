package com.nullptr.one.model.imagedetail;

import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.model.imagedetail.ImageDetailModel;
import com.nullptr.one.model.imagedetail.OnImageDetailListener;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION ImageDetailModel接口实现类
 */
public class ImageDetailModelImpl implements ImageDetailModel {

    @Override
    public void getImageDetailList(final OnImageDetailListener onImageDetailListener) {
        HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/hp/idlist/{id}", "0", new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                final List<String> imageList = JsonUtil.parseJsonToImageList(response);
                final List<ImageDetail> imageDetailList = new ArrayList<>();
                //获得图片列表后，发送请求图片详情请求
                for (final String imageId : imageList) {
                    HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/hp/detail/{id}", imageId, new OnRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            ImageDetail imageDetail = JsonUtil.parseJsonToImageDetail(response);
                            imageDetailList.add(imageDetail);
                            if (imageId.equals(imageList.get(imageList.size()-1))) {
                                //如果是最后一个id，就反馈
                                onImageDetailListener.onSuccess(imageDetailList);
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                            onImageDetailListener.onFail(errorMsg);
                        }

                        @Override
                        public void onStart() {
                            onImageDetailListener.onStart();
                        }

                        @Override
                        public void onFinish() {
                            onImageDetailListener.onFinish();
                        }
                    });
                }
            }

            @Override
            public void onError(String errorMsg) {
                onImageDetailListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }
        });
    }
}
