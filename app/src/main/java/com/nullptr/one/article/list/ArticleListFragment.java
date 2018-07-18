package com.nullptr.one.article.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.nullptr.one.R;
import com.nullptr.one.article.detail.ArticleDetailActivity;
import com.nullptr.one.article.list.IArticleList.ArticleListPresenter;
import com.nullptr.one.article.list.IArticleList.ArticleListView;
import com.nullptr.one.base.BaseAdapter.OnItemClickListener;
import com.nullptr.one.base.OnMoreScrollListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/11
 * @DESCRIPTION 文章Tab所对应的Fragment。
 */
public class ArticleListFragment extends Fragment implements ArticleListView,
        OnItemClickListener {

    private List<Article> mArticleList;
    private RecyclerView mRvList;
    private ArticleAdapter mAdapter;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ArticleListPresenter mArticleListPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_article, container, false);
        mArticleListPresenter = new ArticleListPresenterImpl(this);
        mSrlSwipeRefreshLayout = v.findViewById(R.id.article_srl_swipe_refresh);
        mRvList = v.findViewById(R.id.article_rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRvList.setLayoutManager(manager);

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
            mAdapter = new ArticleAdapter(new ArrayList<Article>(),
                    R.layout.item_list_article, 10);
            mRvList.setAdapter(mAdapter);
            //设置加载更多监听
            mRvList.setOnScrollListener(new OnMoreScrollListener(mRvList) {
                @Override
                public void onLoadMore() {
                    //获取更多数据
                    String lastId = mArticleList.get(mArticleList.size() - 1).getId();
                    mArticleListPresenter.loadMore(lastId);
                }
            });
            mAdapter.setOnItemClickListener(this);           //设置单击Item事件
            //加载初始数据
            mArticleListPresenter.loadList();
        }
    }

    @Override
    public void setArticleList(final List<Article> articleList) {
        //在UI线程更新Adapter的DataList
        mAdapter.setDatas(articleList);
        mArticleList = articleList;
    }

    @Override
    public void showError(final String errorMsg) {
        //网络出错的处理
        Toast.makeText(getActivity(), "网络出错，请检查网络设置", Toast.LENGTH_SHORT).show();
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
        mAdapter.setDatas(mArticleList);
    }

    @Override
    public void onItemClick(View view, int position) {
        String itemId = mArticleList.get(position).getItemId();
        ArticleDetailActivity.actionStart(getActivity(), itemId);
    }
}
