package com.nullptr.one.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.nullptr.one.R;
import com.nullptr.one.bean.ImageDetail;
import com.nullptr.one.presenter.ImageDetailPresenterImpl;
import com.nullptr.one.presenter.interfaces.IDetailPresenter.ImageDetailPresenter;
import com.nullptr.one.view.adapter.BannerAdapter;
import com.nullptr.one.view.interfaces.IDetailView.ImageDetailView;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/20
 * @DESCRIPTION 图文展示Activity
 */
public class ImageDetailActivity extends BaseActivity implements ImageDetailView {

    private ImageDetailPresenter mPresenter;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ViewPager mVpBanner;
    private WeakHandler mHandler;

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
            sendEmptyMessageDelayed(0, 5000);
        }
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
        mSrlSwipeRefreshLayout = findViewById(R.id.image_srl_swipe_refresh);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BannerAdapter adapter = new BannerAdapter(ImageDetailActivity.this,
                        imageDetailList);
                mVpBanner.setAdapter(adapter);
                mHandler = new WeakHandler(ImageDetailActivity.this);
                mHandler.sendEmptyMessageDelayed(0, 5000);   //开启自动轮播 5s一次
            }
        });

    }

    @Override
    public void showError(final String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(
                        ImageDetailActivity.this);
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
                //加载完就不再能刷新(有时候ViewPager左右滑动会被判断为下拉)
                mSrlSwipeRefreshLayout.setEnabled(false);
            }
        });
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
}
