package com.nullptr.one.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.comment.IComment.CommentPresenter;
import com.nullptr.one.comment.IComment.CommentView;
import java.util.ArrayList;
import java.util.List;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION 评论Activity
 */
public class CommentActivity extends BaseActivity implements CommentView {

    private String mItemId;
    private String mType;
    private ListView mLvListView;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private List<Comment> mCommentList;
    private CommentAdapter mAdapter;
    private CommentPresenter mCommentPresenter;

    public static void actionStart(Context context, String itemId, String type) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("item_id", itemId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("item_id");
        mType = intent.getStringExtra("type");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment);
        initToolbar("评论");

        mLvListView = findViewById(R.id.comment_lv_listview);
        mSrlSwipeRefreshLayout = findViewById(R.id.comment_srl_swipe_refresh);
        mCommentPresenter = new CommentPresenterImpl(this);

        mSrlSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCommentPresenter.getCommentList(mItemId, mType);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void loadData() {
        if (mCommentList == null || mCommentList.size() == 0) {
            //不是每次都要刷新的，之前有数据的时候不需要刷新
            mAdapter = new CommentAdapter(this, new ArrayList<Comment>(), R.layout.item_comment);
            mLvListView.setAdapter(mAdapter);
            mCommentPresenter.getCommentList(mItemId, mType);
        }
    }

    @Override
    public void setCommentList(List<Comment> commentList) {
        mAdapter.setDataList(commentList);
        mCommentList = commentList;
    }

    @Override
    public void showError(String errorMsg) {
        //网络出错的处理
        Toast.makeText(this, "网络出错，请检查网络设置", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mSrlSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSrlSwipeRefreshLayout.setRefreshing(false);
    }

    private Toolbar initToolbar(CharSequence title) {
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        return toolbar;
    }

}
