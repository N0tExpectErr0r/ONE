package com.nullptr.one.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.image.IImageDetail.ImageDetailPresenter;
import com.nullptr.one.image.IImageDetail.ImageDetailView;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION 图文展示Activity
 */
public class ImageDetailActivity extends BaseActivity implements ImageDetailView {

    private ImageDetailPresenter mPresenter;
    private ViewPager mVpBanner;
    private WeakHandler mHandler;
    private ProgressBar mPbLoading;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_image);
        initToolbar("一个图文");

        mPresenter = new ImageDetailPresenterImpl(this);
        mVpBanner = findViewById(R.id.image_vp_banner);
        mPbLoading = findViewById(R.id.image_pb_loading);
    }

    @Override
    protected void loadData() {
        mPresenter.getImageDetailList();
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

    @Override
    public void setImageDetailList(final List<ImageDetail> imageDetailList) {

        BannerAdapter adapter = new BannerAdapter(ImageDetailActivity.this,
                imageDetailList);
        mVpBanner.setAdapter(adapter);
        mHandler = new WeakHandler(ImageDetailActivity.this);
        mHandler.sendEmptyMessageDelayed(0, 2000);   //开启自动轮播 2s一次
    }

    @Override
    public void showError(final String errorMsg) {
        //网络出错处理
        Toast.makeText(this, "网络出错，请检查网络设置", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
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
    protected void onDestroy() {
        super.onDestroy();
        //移除所有消息
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private static class WeakHandler extends Handler {

        WeakReference<ImageDetailActivity> mWeakReference;

        WeakHandler(ImageDetailActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ImageDetailActivity activity = mWeakReference.get();
            //让ViewPager滑到下一页
            activity.mVpBanner.setCurrentItem(activity.mVpBanner.getCurrentItem() + 1);
            //延时，循环调用handler
            sendEmptyMessageDelayed(0, 2000);
        }
    }
}
