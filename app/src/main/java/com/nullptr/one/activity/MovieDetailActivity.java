package com.nullptr.one.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.PeriodicSync;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.nullptr.one.R;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.presenter.moviedetail.MovieDetailPresenter;
import com.nullptr.one.presenter.moviedetail.MovieDetailPresenterImpl;
import com.nullptr.one.presenter.movieinfo.MovieInfoPresenter;
import com.nullptr.one.presenter.movieinfo.MovieInfoPresenterImpl;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView{
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

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("item_id");
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

        mSrlSwipeRefreshLayout = findViewById(R.id.movie_detail_srl_swipe_refresh);
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieDetailPresenter.getMovieDetail(mItemId);
                mMovieInfoPresenter.getMovieInfo(mItemId);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvAuthorName.setText("文/"+musicDetail.getAuthor().getName());
                mTvContent.setText(Html.fromHtml(musicDetail.getContent()));
            }
        });
    }

    @Override
    public void setMovieInfo(final MovieInfo musicInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIvCover.setImageBitmap(musicInfo.getCover());
                mTvMovieTitle.setText(musicInfo.getMovieTitle());
                mTvStory.setText(musicInfo.getStory());
                mTvInfo.setText(musicInfo.getInfo());
            }
        });
    }

    @Override
    public void showError(final String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(MovieDetailActivity.this);
                errorDialog
                        .setTitle("错误")
                        .setMessage(errorMsg)
                        .show();
                //关闭App
                finish();
            }
        });
    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSrlSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSrlSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
