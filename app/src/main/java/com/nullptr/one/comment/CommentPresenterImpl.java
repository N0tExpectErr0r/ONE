package com.nullptr.one.comment;

import com.nullptr.one.comment.IComment.CommentModel;
import com.nullptr.one.comment.IComment.CommentPresenter;
import com.nullptr.one.comment.IComment.CommentView;
import com.nullptr.one.comment.IComment.OnCommentListListener;
import java.util.List;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public class CommentPresenterImpl implements CommentPresenter, OnCommentListListener {

    private CommentView mCommentView;
    private CommentModel mCommentModel;

    CommentPresenterImpl(CommentView commentView) {
        mCommentView = commentView;
        mCommentModel = new CommentModelImpl();
    }

    @Override
    public void getCommentList(String itemId, String type) {
        mCommentModel.getCommentList(CommentPresenterImpl.this, itemId, type);
    }

    @Override
    public void onSuccess(final List<Comment> commentList) {
        mCommentView.setCommentList(commentList);
    }

    @Override
    public void onFail(final String errorMsg) {
        mCommentView.showError(errorMsg);
    }

    @Override
    public void onStart() {
        mCommentView.showLoading();
    }

    @Override
    public void onFinish() {
        mCommentView.hideLoading();
    }
}
