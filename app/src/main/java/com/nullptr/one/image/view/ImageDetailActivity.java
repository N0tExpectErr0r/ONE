package com.nullptr.one.image.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.image.IImageDetail.ImageDetailPresenter;
import com.nullptr.one.image.IImageDetail.ImageDetailView;
import com.nullptr.one.image.adapter.BannerAdapter;
import com.nullptr.one.image.presenter.ImageDetailPresenterImpl;
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

    // Handler的内存泄漏问题
    // 比如用Handler发送一条延时消息，此时关闭Activity
    // 此时的Message持有Handler的引用，Handler又持有Activity的引用，
    // 因此Activity就不会被回收，导致内存泄漏.
    // 所以换另一种写法
    //
    // Handler handler = new Handler() {
    //     public void handleMessage(android.os.Message msg) {
    //         //让ViewPager滑到下一页
    //         mVpBanner.setCurrentItem(mVpBanner.getCurrentItem() + 1);
    //         //延时，循环调用handler
    //         handler.sendEmptyMessageDelayed(0, 5000);
    //     }
    //
    //     ;
    // };

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
        Log.d("ImageDetailActivity","load");
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

        AlertDialog.Builder errorDialog = new AlertDialog.Builder(
                ImageDetailActivity.this);
        errorDialog
                .setTitle("错误")
                .setMessage(errorMsg)
                .show();
        //关闭App
        finish();

    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
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
    protected void onDestroy() {
        super.onDestroy();
        //移除所有消息
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class WeakHandler extends Handler {

        WeakReference<ImageDetailActivity> mWeakReference;

        public WeakHandler(ImageDetailActivity activity) {
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
