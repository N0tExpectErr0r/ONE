package com.nullptr.one.moduleComment.presenter;

import android.os.Handler;
import android.os.Looper;
import com.nullptr.one.bean.Comment;
import com.nullptr.one.moduleComment.IComment.CommentModel;
import com.nullptr.one.moduleComment.IComment.CommentPresenter;
import com.nullptr.one.moduleComment.IComment.CommentView;
import com.nullptr.one.moduleComment.IComment.OnCommentListListener;
import com.nullptr.one.moduleComment.model.CommentModelImpl;
import java.util.List;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public class CommentPresenterImpl implements CommentPresenter,OnCommentListListener {

    private CommentView mCommentView;
    private CommentModel mCommentModel;
    private Handler mUiHandler;

    public CommentPresenterImpl(CommentView commentView){
        mCommentView = commentView;
        mCommentModel = new CommentModelImpl();
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getCommentList(String itemId, String type) {
        mCommentModel.getCommentList(CommentPresenterImpl.this,itemId,type);
    }

    @Override
    public void onSuccess(final List<Comment> commentList) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mCommentView.setCommentList(commentList);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mCommentView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onStart() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mCommentView.showLoading();
            }
        });
    }

    @Override
    public void onFinish() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mCommentView.hideLoading();
            }
        });
    }
}
