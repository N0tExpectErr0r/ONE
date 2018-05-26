package com.nullptr.one.moduleImage.model;

import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.moduleImage.IImageDetail.ImageDetailModel;
import com.nullptr.one.moduleImage.IImageDetail.OnImageDetailListener;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;
import java.util.Arrays;
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
        final StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/hp/idlist/")
                .append("0")
                .append("?version=3.5.0&platform=android");
        HttpUtil.sendHttpRequest(url.toString(),
                new OnRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        final List<String> imageList = JsonUtil.parseJsonToImageList(response);
                        final ImageDetail[] imageArr = new ImageDetail[10];
                        //获得图片列表后，发送请求图片详情请求
                        for (int i = 0; i < 10; i++) {
                            String imageId = imageList.get(i);
                            final int currentIndex = i;
                            StringBuilder imageUrl = new StringBuilder();
                            imageUrl.append("http://v3.wufazhuce.com:8000/api/hp/detail/")
                                    .append(imageId)
                                    .append("?version=3.5.0&platform=android");
                            HttpUtil.sendHttpRequest(imageUrl.toString(), new OnRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    ImageDetail imageDetail = JsonUtil
                                            .parseJsonToImageDetail(response);
                                    //由于加载图片速度不同
                                    //用数组来按获取到的顺序放，最后转为List
                                    imageArr[currentIndex] = imageDetail;
                                    if (imageList.get(currentIndex)
                                            .equals(imageList.get(imageList.size() - 1))) {
                                        //如果是最后一个id，就反馈
                                        onImageDetailListener
                                                .onSuccess(Arrays.asList(imageArr));
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
