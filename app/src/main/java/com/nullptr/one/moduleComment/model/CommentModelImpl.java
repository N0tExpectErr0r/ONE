package com.nullptr.one.moduleComment.model;

import com.nullptr.one.bean.Comment;
import com.nullptr.one.moduleComment.IComment.CommentModel;
import com.nullptr.one.moduleComment.IComment.OnCommentListListener;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;
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
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/comment/praiseandtime/")
                .append(commentType)
                .append("/" + itemId)
                .append("/0?&platform=android");
        HttpUtil.sendHttpRequest(url.toString(),
                new OnRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        //将该文章详情存入数据库
                        List<Comment> articleDetail = JsonUtil
                                .parseJsonToCommentList(response);
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
