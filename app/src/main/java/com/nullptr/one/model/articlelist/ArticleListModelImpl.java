package com.nullptr.one.model.articlelist;

import com.nullptr.one.bean.Article;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION ArticleModel实现类
 */
public class ArticleListModelImpl implements ArticleListModel {

    @Override
    public void getList(final OnArticleListListener onArticleListListener) {
        HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/channel/reading/more/{id}", "0", new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Article> articleList = JsonUtil.parseJsonToArticleList(response);
                onArticleListListener.onSuccess(articleList);
            }

            @Override
            public void onError(String errorMsg) {
                onArticleListListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onArticleListListener.onStart();
            }

            @Override
            public void onFinish() {
                onArticleListListener.onFinish();
            }
        });
    }

    @Override
    public void getMore(final OnMoreArticleListener onMoreArticleListener, String lastId) {
        HttpUtil.sendHttpRequest("http://v3.wufazhuce.com:8000/api/channel/reading/more/{id}", lastId, new OnRequestListener() {
            @Override
            public void onResponse(String response) {
                List<Article> articleList = JsonUtil.parseJsonToArticleList(response);
                onMoreArticleListener.onMoreSuccess(articleList);
            }

            @Override
            public void onError(String errorMsg) {
                onMoreArticleListener.onFail(errorMsg);
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
