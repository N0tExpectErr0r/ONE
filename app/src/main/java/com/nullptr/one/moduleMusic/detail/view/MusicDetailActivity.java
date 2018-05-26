package com.nullptr.one.moduleMusic.detail.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.bean.MusicDetail;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.MusicDetailPresenter;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.MusicDetailView;
import com.nullptr.one.moduleMusic.detail.presenter.MusicDetailPresenterImpl;
import com.nullptr.one.util.HtmlPraser;
import com.nullptr.one.util.ImageLoader;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐详细内容Activity
 */
public class MusicDetailActivity extends BaseActivity implements MusicDetailView {

    final public static String ACTION = "MUSIC_DETAIL";

    private String mItemId;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvSummary;
    private TextView mTvContent;
    private TextView mTvAuthorName;
    private TextView mTvDate;
    private TextView mTvMusicName;
    private TextView mTvMusicInfo;
    private TextView mTvMusicLyric;
    private MusicDetailPresenter mMusicDetailPresenter;

    public static void actionStart(Context context, String itemId) {
        Intent intent = new Intent(context, MusicDetailActivity.class);
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
        mTvContent = findViewById(R.id.music_detail_tv_music_story);
        mTvAuthorName = findViewById(R.id.music_detail_tv_music_author);
        mTvDate = findViewById(R.id.music_detail_tv_music_date);
        mTvMusicName = findViewById(R.id.music_detail_tv_music_name);
        mTvMusicInfo = findViewById(R.id.music_detail_tv_music_info);
        mTvMusicLyric = findViewById(R.id.music_detail_tv_music_lyric);

        mSrlSwipeRefreshLayout = findViewById(R.id.music_detail_srl_swipe_refresh);
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMusicDetailPresenter.getMusicDetail(mItemId);
            }
        });
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
    public void setMusic(final MusicDetail music) {
        ImageLoader.getInstance().loadImg(mIvImage, music.getCoverURL());
        mTvTitle.setText(music.getStoryTitle());
        mTvSummary.setText(music.getStorySummary());
        mTvAuthorName.setText(music.getAuthor().getName());
        mTvDate.setText(music.getDate());
        mTvMusicName.setText(music.getTitle());
        mTvMusicInfo.setText(music.getInfo());
        mTvMusicLyric.setText(music.getLyric());
        HtmlPraser.getInstance().setHtml(mTvContent, music.getStory());
    }

    @Override
    public void showError(final String errorMsg) {
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(MusicDetailActivity.this);
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