package com.nullptr.one.model.articlelist;

import com.nullptr.one.bean.Article;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 供给Presenter层的监听接口
 */
public interface OnMoreArticleListener {

    void onMoreSuccess(List<Article> articleList);

    void onFail(String errorMsg);
}
