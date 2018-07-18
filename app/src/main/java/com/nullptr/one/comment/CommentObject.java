package com.nullptr.one.comment;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class CommentObject {

    @SerializedName("data")
    private CommentList commentData;

    public CommentList getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentList commentData) {
        this.commentData = commentData;
    }
}
