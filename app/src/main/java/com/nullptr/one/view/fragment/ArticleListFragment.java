package com.nullptr.one.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.nullptr.one.R;
import com.nullptr.one.view.adapter.ArticleAdapter;
import com.nullptr.one.bean.Article;
import com.nullptr.one.presenter.ArticleListPresenterImpl;
import com.nullptr.one.presenter.interfaces.ListPresenter;
import com.nullptr.one.view.activity.ArticleDetailActivity;
import com.nullptr.one.view.interfaces.IListView.ArticleListView;
import com.nullptr.one.view.ui.LoadMoreListView;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/11
 * @DESCRIPTION 文章Tab所对应的Fragment。
 */
public class ArticleListFragment extends Fragment implements ArticleListView,
        ListView.OnItemClickListener, LoadMoreListView.OnLoadMoreListener {

    private List<Article> mArticleList;
    private LoadMoreListView mLvListView;
    private ArticleAdapter mAdapter;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ListPresenter mArticleListPresenter;


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
                mArticleListPresenter.loadList();
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在UI线程更新Adapter的DataList
                mAdapter.setDataList(articleList);
                mArticleList = articleList;
            }
        });

    }

    @Override
    public void showError(final String errorMsg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //UI线程进行UI操作
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity());
                errorDialog
                        .setTitle("错误")
                        .setMessage(errorMsg)
                        .show();
                //关闭App
                getActivity().finish();
            }
        });
    }

    @Override
    public void showLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //UI线程进行UI操作
                mSrlSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //UI线程进行UI操作
                mSrlSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void addArticleList(final List<Article> articleList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mArticleList.addAll(articleList);
                mAdapter.setDataList(mArticleList);
                mLvListView.setLoadCompleted();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemId = mArticleList.get(position).getItemId();
        Intent intent = new Intent(ArticleDetailActivity.ACTION);
        intent.putExtra("item_id", itemId);
         startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        //ListView加载更多的回调
        //获取更多数据
        String lastId = mArticleList.get(mArticleList.size() - 1).getId();
        mArticleListPresenter.loadMore(lastId);
    }
}
