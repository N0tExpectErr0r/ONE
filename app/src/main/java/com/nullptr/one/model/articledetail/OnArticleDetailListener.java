package com.nullptr.one.model.articledetail;

import com.nullptr.one.bean.ArticleDetail;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnArticleDetailListener {

    void onSuccess(ArticleDetail articleDetail);

    void onFail(String errorMsg);

    void onStart();

    void onFinish();
}
