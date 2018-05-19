package com.nullptr.one.ui;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nullptr.one.R;
import com.nullptr.one.util.ImageUtil;
import java.util.List;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/18
 * @DESCRIPTION 轮播图控件
 */
public class BannerPager extends ViewPager implements OnTouchListener, OnPageChangeListener {

    private List<String> mImageList;
    private List<String> mTitleList;

    private Context mContext;
    private boolean hasAdapter = false;
    private int mCurrentItem = 0;
    private int downX;
    private int downY;
    private Task mTask;
    private long downTimeMillis;
    private ViewPageOnTouchListener mViewPageOnTouchListener;


    // 是否滑动
    private boolean isMove = false;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            setCurrentItem(mCurrentItem, false);
            start();
        }
    };

    public BannerPager(Context context) {
        super(context);
        mContext = context;
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            downTimeMillis = System.currentTimeMillis();    //获取按下时间

            // 停止跳动
            // 删除消息
            handler.removeCallbacksAndMessages(null);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            long currentTimeMillis = System.currentTimeMillis();    //获取释放时间
            if (currentTimeMillis - downTimeMillis < 500) {
                //如果是点击而不是长按
                if (mViewPageOnTouchListener != null) {
                    mViewPageOnTouchListener.onViewPageClickListener();     //触发点击事件
                }
            }
            start();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            /**
             * 摘自CSDN
             * 在设计设置页面的滑动开关时，如果不监听ACTION_CANCEL，在滑动到中间时，如果你手指上下移动，
             * 就是移动到开关控件之外，则此时会触发ACTION_CANCEL，而不是ACTION_UP，造成开关的按钮停顿在中间位置。
             * 意思就是，当用户保持按下操作，并从你的控件转移到外层控件时，会触发ACTION_CANCEL，建议进行处理
             * 因此在此处理
             */
            start();
        }
        return false;
    }

    public void start() {
        if (!hasAdapter) {
            hasAdapter = true;
            BannerPagerAdapter adapter = new BannerPagerAdapter();
            BannerPager.this.setAdapter(adapter);
            BannerPager.this.setOnPageChangeListener(this);
        }
        handler.postDelayed(mTask, 2000);
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setViewPageOnTouchListener(ViewPageOnTouchListener viewPageOnTouchListener) {
        mViewPageOnTouchListener = viewPageOnTouchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) motionEvent.getX();
                downY = (int) motionEvent.getY();
                break;

            case MotionEvent.ACTION_MOVE:

                int currentX = (int) motionEvent.getX();
                int currentY = (int) motionEvent.getY();

                if (Math.abs(currentX - downX) > Math.abs(currentY - downY)) {
                    // 左右滑动viewPager
                    isMove = false;
                } else {
                    // 上下滑动listview
                    isMove = true;
                }
                break;
        }
        // 请求父类不要拦截
        getParent().requestDisallowInterceptTouchEvent(!isMove);

        return super.dispatchTouchEvent(motionEvent);
    }

    public interface ViewPageOnTouchListener {

        void onViewPageClickListener();     //page点击事件
    }

    private class Task implements Runnable {

        @Override
        public void run() {
            mCurrentItem = (mCurrentItem + 1) % mImageList.size();
            handler.obtainMessage().sendToTarget();
        }

    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.item_bannerpager, null);
            ImageView image = view.findViewById(R.id.bannerpager_iv_image);
            TextView title = view.findViewById(R.id.bannerpager_tv_title);
            ImageUtil.loadImg(image, mImageList.get(position));
            title.setText(mTitleList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setImageList(List<String> imageList) {
            mImageList = imageList;
        }

        public void setTitleList(List<String> titleList) {
            mTitleList = titleList;
        }
    }
}
