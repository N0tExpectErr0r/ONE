package com.nullptr.one.model.articledetail;

import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleDetailModel接口实现类
 */
public class ArticleDetailModelImpl implements ArticleDetailModel {

    @Override
    public void getArticleDetail(final OnArticleDetailListener onArticleDetailListener,
            String itemId) {
        HttpUtil.sendHttpRequest("getArticleDetail", itemId, new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                ArticleDetail articleDetail = JsonUtil.parseJsonToArticleDetail(response);
                onArticleDetailListener.onSuccess(articleDetail);
            }

            @Override
            public void onError(String errorMsg) {
                onArticleDetailListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onArticleDetailListener.onStart();
            }

            @Override
            public void onFinish() {
                onArticleDetailListener.onFinish();
            }
        });
    }
}
