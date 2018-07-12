package com.nullptr.one.movie.detail.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.comment.view.CommentActivity;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailPresenter;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailView;
import com.nullptr.one.movie.detail.IMovieDetail.MovieInfoPresenter;
import com.nullptr.one.movie.detail.presenter.MovieDetailPresenterImpl;
import com.nullptr.one.movie.detail.presenter.MovieInfoPresenterImpl;
import com.nullptr.one.util.HtmlPraser;
import com.nullptr.one.util.ImageLoader;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView {

    private String mItemId;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieInfoPresenter mMovieInfoPresenter;
    private ImageView mIvCover;
    private TextView mTvMovieTitle;
    private TextView mTvInfo;
    private TextView mTvStory;
    private TextView mTvAuthorName;
    private TextView mTvContent;
    private FloatingActionButton mFabComment;

    public static void actionStart(Context context, String itemId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("item_id", itemId);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("item_id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_movie);
        initToolbar("一个影视");

        mIvCover = findViewById(R.id.movie_detail_iv_cover);
        mTvMovieTitle = findViewById(R.id.movie_detail_tv_movie_title);
        mTvInfo = findViewById(R.id.movie_detail_tv_info);
        mTvStory = findViewById(R.id.movie_detail_tv_story);
        mTvAuthorName = findViewById(R.id.movie_detail_tv_author_name);
        mTvContent = findViewById(R.id.movie_detail_tv_content);
        mFabComment = findViewById(R.id.movie_detail_fab_comment);

        mSrlSwipeRefreshLayout = findViewById(R.id.movie_detail_srl_swipe_refresh);
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieDetailPresenter.getMovieDetail(mItemId);
                mMovieInfoPresenter.getMovieInfo(mItemId);
            }
        });

        mFabComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(MovieDetailActivity.this,mItemId,"MOVIE");
            }
        });
    }

    @Override
    protected void loadData() {
        mMovieDetailPresenter = new MovieDetailPresenterImpl(this);
        mMovieInfoPresenter = new MovieInfoPresenterImpl(this);
        mMovieDetailPresenter.getMovieDetail(mItemId);
        mMovieInfoPresenter.getMovieInfo(mItemId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //菜单按钮,返回
                finish();
                break;
            default:
        }
        return true;
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

    @Override
    public void setMovieDetail(final MovieDetail musicDetail) {
        mTvAuthorName.setText("文/" + musicDetail.getAuthor().getName());
        HtmlPraser.getInstance().setHtml(mTvContent, musicDetail.getContent());
    }

    @Override
    public void setMovieInfo(final MovieInfo musicInfo) {
        mTvMovieTitle.setText(musicInfo.getMovieTitle());
        mTvStory.setText(musicInfo.getStory());
        mTvInfo.setText(musicInfo.getInfo());
        ImageLoader.getInstance().loadImg(mIvCover, musicInfo.getCoverURL());
    }

    @Override
    public void showError(final String errorMsg) {
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(MovieDetailActivity.this);
        errorDialog
                .setTitle("错误")
                .setMessage(errorMsg)
                .show();
        //关闭App
        finish();
    }

    @Override
    public void showLoading() {
        mSrlSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSrlSwipeRefreshLayout.setRefreshing(false);
    }

}
