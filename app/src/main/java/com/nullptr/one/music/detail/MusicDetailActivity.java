package com.nullptr.one.music.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.comment.CommentActivity;
import com.nullptr.one.music.detail.IMusicDetail.MusicDetailPresenter;
import com.nullptr.one.music.detail.IMusicDetail.MusicDetailView;
import com.nullptr.one.util.BitmapCache;
import com.nullptr.one.util.HtmlUtil;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐详细内容Activity
 */
public class MusicDetailActivity extends BaseActivity implements MusicDetailView {

    private String mItemId;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvSummary;
    private WebView mWvContent;
    private TextView mTvAuthorName;
    private TextView mTvDate;
    private TextView mTvMusicName;
    private TextView mTvMusicInfo;
    private TextView mTvMusicLyric;
    private MusicDetailPresenter mMusicDetailPresenter;

    public static void actionStart(Context context, String itemId) {
        Intent intent = new Intent(context, MusicDetailActivity.class);
        Log.d("MusicList", itemId);
        intent.putExtra("item_id", itemId);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("item_id");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_music);
        initToolbar("一个音乐");

        mIvImage = findViewById(R.id.music_detail_iv_music_image);
        mTvTitle = findViewById(R.id.music_detail_tv_music_title);
        mTvSummary = findViewById(R.id.music_detail_tv_music_summary);
        mWvContent = findViewById(R.id.music_detail_wv_story);
        mWvContent.getSettings().setSupportZoom(false);
        mTvAuthorName = findViewById(R.id.music_detail_tv_music_author);
        mTvDate = findViewById(R.id.music_detail_tv_music_date);
        mTvMusicName = findViewById(R.id.music_detail_tv_music_name);
        mTvMusicInfo = findViewById(R.id.music_detail_tv_music_info);
        mTvMusicLyric = findViewById(R.id.music_detail_tv_music_lyric);
        FloatingActionButton fabComment = findViewById(R.id.music_detail_fab_comment);

        mSrlSwipeRefreshLayout = findViewById(R.id.music_detail_srl_swipe_refresh);
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMusicDetailPresenter.getMusicDetail(mItemId);
            }
        });

        fabComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(MusicDetailActivity.this, mItemId, "MUSIC");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void loadData() {
        mMusicDetailPresenter = new MusicDetailPresenterImpl(this);
        mMusicDetailPresenter.getMusicDetail(mItemId);
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
    public void setMusic(final MusicDetail music) {
        mTvTitle.setText(music.getStoryTitle());
        mTvSummary.setText(music.getStorySummary());
        mTvAuthorName.setText(music.getAuthor().getName());
        mTvDate.setText(music.getDate());
        mTvMusicName.setText(music.getTitle());
        mTvMusicInfo.setText(music.getInfo());
        mTvMusicLyric.setText(music.getLyric());

        ImageLoader loader = new ImageLoader(
                ContextApplication.getHttpQueues(), new BitmapCache());

        ImageListener listener = com.android.volley.toolbox.ImageLoader
                .getImageListener(mIvImage, R.drawable.mock, R.drawable.mock);

        loader.get(music.getCoverURL(), listener, mIvImage.getWidth(), mIvImage.getHeight());

        mWvContent.loadDataWithBaseURL(null, HtmlUtil.getNewContent(music.getStory()),
                "text/html", "UTF-8", null);
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
