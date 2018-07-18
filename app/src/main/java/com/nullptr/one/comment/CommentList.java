package com.nullptr.one.comment;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class CommentList {

    @SerializedName("data")
    private List<Comment> commentList;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
