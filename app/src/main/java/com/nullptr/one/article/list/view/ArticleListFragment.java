package com.nullptr.one.article.list.view;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.bean.Article;
import com.nullptr.one.article.detail.view.ArticleDetailActivity;
import com.nullptr.one.article.list.IArticleList.ArticleListPresenter;
import com.nullptr.one.article.list.IArticleList.ArticleListView;
import com.nullptr.one.article.list.adapter.ArticleAdapter;
import com.nullptr.one.article.list.presenter.ArticleListPresenterImpl;
import com.nullptr.one.main.view.MainActivity;
import com.nullptr.one.ui.LoadMoreListView;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/11
 * @DESCRIPTION 文章Tab所对应的Fragment。
 */
public class ArticleListFragment extends Fragment implements ArticleListView,
        ListView.OnItemClickListener, LoadMoreListView.OnLoadMoreListener {

    private NotificationManager mManager;
    private List<Article> mArticleList;
    private LoadMoreListView mLvListView;
    private ArticleAdapter mAdapter;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ArticleListPresenter mArticleListPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_article, container, false);
        mArticleListPresenter = new ArticleListPresenterImpl(this);
        mSrlSwipeRefreshLayout = v.findViewById(R.id.article_srl_swipe_refresh);
        mLvListView = v.findViewById(R.id.article_lv_listview);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * 初始化View结束后再发送请求等各种操作
         * 这样做的原因是因为之前用别人的华为P10测试的时候发现SwipeRefreshLayout没有进度条显示
         * 原因是由于SwipeRefreshLayout没有加载完就对它进行了showLoading操作
         * 参考了这篇文章https://blog.csdn.net/wang8651971/article/details/50820965
         * 刚才测试了一下，问题已解决
         */

        //初始化SwipeRefreshLayout
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新 加载初始数据
                mArticleListPresenter.updateList();
            }
        });
        if (mArticleList == null || mArticleList.size() == 0) {
            //不是每次都要刷新的，之前有数据的时候不需要刷新
            //初始化ListView
            mAdapter = new ArticleAdapter(getActivity(), new ArrayList<Article>(),
                    R.layout.item_list_article);
            mLvListView.setAdapter(mAdapter);
            mLvListView.setFooterText("正在加载更多文章...");     //设置加载更多文字
            mLvListView.setLoadMoreListener(this);              //设置加载更多监听
            mLvListView.setOnItemClickListener(this);           //设置单击Item事件
            //加载初始数据
            mArticleListPresenter.loadList();
        }
    }

    @Override
    public void setArticleList(final List<Article> articleList) {
        //在UI线程更新Adapter的DataList
        mAdapter.setDataList(articleList);
        mArticleList = articleList;
    }

    @Override
    public void showError(final String errorMsg) {
        //网络出错的处理
        Toast.makeText(getActivity(),"网络出错，请检查网络设置",Toast.LENGTH_SHORT).show();
        mLvListView.setLoadCompleted();
        mSrlSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        //UI线程进行UI操作
        mSrlSwipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void hideLoading() {
        //UI线程进行UI操作
        mSrlSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addArticleList(final List<Article> articleList) {
        mArticleList.addAll(articleList);
        mAdapter.setDataList(mArticleList);
        mLvListView.setLoadCompleted();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemId = mArticleList.get(position).getItemId();
        ArticleDetailActivity.actionStart(getActivity(), itemId);
    }

    @Override
    public void onLoadMore() {
        //ListView加载更多的回调
        //获取更多数据
        String lastId = mArticleList.get(mArticleList.size() - 1).getId();
        mArticleListPresenter.loadMore(lastId);
    }


}
