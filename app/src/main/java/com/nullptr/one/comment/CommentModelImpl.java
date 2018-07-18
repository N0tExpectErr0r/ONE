package com.nullptr.one.comment;

import com.nullptr.one.comment.IComment.CommentModel;
import com.nullptr.one.comment.IComment.OnCommentListListener;
import com.nullptr.one.net.HttpListener;
import com.nullptr.one.net.Request;
import com.nullptr.one.net.RequestExecutor;
import com.nullptr.one.net.Response;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.UrlUtil;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public class CommentModelImpl implements CommentModel {

    @Override
    public void getCommentList(final OnCommentListListener onCommentListListener, String itemId, String type) {
        String commentType = null;
        switch (type) {
            case "ARTICLE":
                commentType = "essay";
                break;
            case "MUSIC":
                commentType = "music";
                break;
            case "MOVIE":
                commentType = "movie";
                break;
        }
        Request request = new Request(UrlUtil.getCommentListUrl(commentType,itemId));
        RequestExecutor.getInstance().execute(request,
                new HttpListener() {
                    @Override
                    public void onResponse(Response response) {
                        //将该文章详情存入数据库
                        List<Comment> articleDetail = JsonUtil
                                .parseJsonToCommentList(response.getResult());
                        onCommentListListener.onSuccess(articleDetail);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        onCommentListListener.onFail(errorMsg);
                    }

                    @Override
                    public void onStart() {
                        onCommentListListener.onStart();
                    }

                    @Override
                    public void onFinish() {
                        onCommentListListener.onFinish();
                    }
                });
    }
}
