package com.nullptr.one.comment;

import com.nullptr.one.bean.Comment;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public interface IComment {
    //Model层
    interface CommentModel {

        void getCommentList(OnCommentListListener onCommentListListener,String itemId,String type);
    }

    interface OnCommentListListener {

        void onSuccess(List<Comment> commentList);

        void onFail(String errorMsg);

        void onStart();

        void onFinish();
    }

    //Presenter层
    interface CommentPresenter {

        void getCommentList(String itemId,String type);
    }

    //View层
    interface CommentView {

        void setCommentList(List<Comment> commentList);

        void showError(String errorMsg);

        void showLoading();

        void hideLoading();
    }
}
