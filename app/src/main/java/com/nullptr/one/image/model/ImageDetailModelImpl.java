package com.nullptr.one.image.model;

import android.util.Log;
import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.image.IImageDetail.ImageDetailModel;
import com.nullptr.one.image.IImageDetail.OnImageDetailListener;
import com.nullptr.one.net.HttpListener;
import com.nullptr.one.net.Request;
import com.nullptr.one.net.RequestExecutor;
import com.nullptr.one.net.Response;
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

        final Request request = new Request(url.toString());
        RequestExecutor.getInstance().execute(request,new HttpListener() {
                    @Override
                    public void onResponse(Response response) {
                        final List<String> imageList = JsonUtil.parseJsonToImageList(response.getResult());
                        final ImageDetail[] imageArr = new ImageDetail[10];
                        //获得图片列表后，发送请求图片详情请求
                        for (int i = 0; i < 10; i++) {
                            String imageId = imageList.get(i);
                            final int currentIndex = i;
                            StringBuilder imageUrl = new StringBuilder();
                            imageUrl.append("http://v3.wufazhuce.com:8000/api/hp/detail/")
                                    .append(imageId)
                                    .append("?version=3.5.0&platform=android");
                            Request imageRequest = new Request(imageUrl.toString());
                            RequestExecutor.getInstance().execute(imageRequest, new HttpListener() {
                                @Override
                                public void onResponse(Response response) {
                                    ImageDetail imageDetail = JsonUtil
                                            .parseJsonToImageDetail(response.getResult());
                                    //由于加载图片速度不同
                                    //用数组来按获取到的顺序放，最后转为List
                                    imageArr[currentIndex] = imageDetail;
                                    boolean isOver = true;
                                    for (int j = 0; j < 10; j++) {
                                        if (imageArr[j] == null) {
                                            isOver = false;
                                            break;
                                        }
                                    }
                                    if (isOver) {
                                        onImageDetailListener
                                                .onSuccess(Arrays.asList(imageArr));
                                    }
                                }

                                @Override
                                public void onError(Exception e) {
                                    onImageDetailListener.onFail(e.getMessage());
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
                    public void onError(Exception e) {
                        onImageDetailListener.onFail(e.getMessage());
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
