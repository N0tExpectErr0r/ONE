package com.nullptr.one.article.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.nullptr.one.base.LazyFragment;
import com.nullptr.one.base.OnMoreScrollListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/11
 * @DESCRIPTION 文章Tab所对应的Fragment。
 */
public class ArticleListFragment extends LazyFragment implements ArticleListView,
        OnItemClickListener {

    private List<Article> mArticleList;
    private RecyclerView mRvList;
    private ArticleAdapter mAdapter;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ArticleListPresenter mArticleListPresenter;


    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_article, container, false);
        mArticleListPresenter = new ArticleListPresenterImpl(this);
        mSrlSwipeRefreshLayout = v.findViewById(R.id.article_srl_swipe_refresh);
        mRvList = v.findViewById(R.id.article_rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRvList.setLayoutManager(manager);

        mSrlSwipeRefreshLayout.setRefreshing(true);
        //初始化SwipeRefreshLayout
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新 加载初始数据
                mArticleListPresenter.updateList();
            }
        });

        return v;
    }

    @Override
    protected void initData() {
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

    @Override
    public void onStart() {
        super.onStart();

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
