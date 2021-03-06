package com.nullptr.one.movie.detail;

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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.comment.CommentActivity;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailPresenter;
import com.nullptr.one.movie.detail.IMovieDetail.MovieDetailView;
import com.nullptr.one.movie.detail.IMovieDetail.MovieInfoPresenter;
import com.nullptr.one.util.BitmapCache;
import com.nullptr.one.util.HtmlUtil;

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
    private WebView mWvContent;

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
        mWvContent = findViewById(R.id.movie_detail_wv_content);
        mWvContent.getSettings().setSupportZoom(false);
        FloatingActionButton fabComment = findViewById(R.id.movie_detail_fab_comment);

        mSrlSwipeRefreshLayout = findViewById(R.id.movie_detail_srl_swipe_refresh);
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieDetailPresenter.getMovieDetail(mItemId);
                mMovieInfoPresenter.getMovieInfo(mItemId);
            }
        });

        fabComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(MovieDetailActivity.this, mItemId, "MOVIE");
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

    private void initToolbar(CharSequence title) {
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public void setMovieDetail(final MovieDetail musicDetail) {
        mTvAuthorName.setText("文/" + musicDetail.getAuthor().getName());
        mWvContent.loadDataWithBaseURL(null,HtmlUtil.getNewContent(musicDetail.getContent()),
                "text/html","UTF-8", null);
    }

    @Override
    public void setMovieInfo(final MovieInfo musicInfo) {
        mTvMovieTitle.setText(musicInfo.getMovieTitle());
        mTvStory.setText(musicInfo.getStory());
        mTvInfo.setText(musicInfo.getInfo());
        com.android.volley.toolbox.ImageLoader loader = new com.android.volley.toolbox.ImageLoader(
                ContextApplication.getHttpQueues(), new BitmapCache());

        ImageListener listener = com.android.volley.toolbox.ImageLoader
                .getImageListener(mIvCover, R.drawable.mock, R.drawable.mock);

        loader.get(musicInfo.getCoverURL(), listener, mIvCover.getWidth(), mIvCover.getHeight());
    }

    @Override
    public void showError(final String errorMsg) {
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

}
