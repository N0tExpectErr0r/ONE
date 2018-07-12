package com.nullptr.one.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.nullptr.one.R;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 自定义View，实现了上拉加载更多
 */
public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private View mFooter;
    private int mTotalItemCount;
    private int mLastVisbleItem;
    private boolean isLoading;
    private OnLoadMoreListener mListener;

    public LoadMoreListView(Context context) {
        super(context);
        initView(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //用LayoutInflater初始化底部布局
        mFooter = inflater.inflate(R.layout.footer_layout, null);
        mFooter.setVisibility(GONE);    //设置底部布局默认不可见
        addFooterView(mFooter);
        setOnScrollListener(this);  //设置滚动监听
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                //滚动停止时
                if (mTotalItemCount == mLastVisbleItem) {
                    //如果滚动到底端最后一个
                    if (!isLoading) {
                        //如果此时并非正在加载
                        isLoading = true;
                        mFooter.setVisibility(VISIBLE);   //显示底部布局
                        mListener.onLoadMore();
                    }
                }
                break;
            default:
                break;
        }

    }


    public void setLoadCompleted() {
        //加载完成，取消显示footer
        isLoading = false;
        mFooter.setVisibility(GONE);
    }

    public void setFooterText(String text) {
        TextView footText = mFooter.findViewById(R.id.foot_text);
        footText.setText(text);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
        this.mLastVisbleItem = firstVisibleItem + visibleItemCount;
        this.mTotalItemCount = totalItemCount;
    }

    public void setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mListener = onLoadMoreListener;
    }

    //加载更多数据的回调接口
    public interface OnLoadMoreListener {

        void onLoadMore();
    }
}
