package com.nullptr.one.comment;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.nullptr.one.R;
import com.nullptr.one.base.CommonAdapter;
import com.nullptr.one.base.ViewHolder;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION
 */
public class CommentAdapter extends CommonAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Comment comment) {
        holder.setText(R.id.comment_tv_content, comment.getComment())
                .setText(R.id.comment_tv_user, comment.getUser() + "：");
        LinearLayout commentView = holder.getConvertView().findViewById(R.id.comment_cv_comment_view);

        if (comment.getToUser() != null) {
            //如果是回复
            holder.setText(R.id.comment_tv_quote, comment.getQuote())
                    .setText(R.id.comment_tv_touser, "@" + comment.getToUser() + "：");
            commentView.setVisibility(View.VISIBLE);
        } else {
            //不是回复，不显示回复框
            holder.setText(R.id.comment_tv_quote, "")
                    .setText(R.id.comment_tv_touser, "");
            commentView.setVisibility(View.GONE);
        }
    }
}
