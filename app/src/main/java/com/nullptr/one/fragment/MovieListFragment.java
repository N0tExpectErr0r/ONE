package com.nullptr.one.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.nullptr.one.activity.MovieDetailActivity;
import com.nullptr.one.activity.MusicDetailActivity;
import com.nullptr.one.presenter.movielist.MovieListPresenter;
import com.nullptr.one.presenter.movielist.MovieListPresenterImpl;
import com.nullptr.one.R;
import com.nullptr.one.adapter.MovieAdapter;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.ui.LoadMoreListView;
import com.nullptr.one.ui.LoadMoreListView.OnLoadMoreListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION
 */
public class MovieListFragment extends Fragment implements OnLoadMoreListener,
        ListView.OnItemClickListener, MovieListView {

    private LoadMoreListView mLvListView;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private MovieListPresenter mMovieListPresenter;
    private MovieAdapter mAdapter;
    private List<Movie> mMovieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_movie, container, false);
        mMovieListPresenter = new MovieListPresenterImpl(this);
        mLvListView = v.findViewById(R.id.movie_lv_listview);
        mSrlSwipeRefreshLayout = v.findViewById(R.id.movie_srl_swipe_refresh);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        //在onStart中初始化原因同ArticleListFragment

        //初始化SwipeRefreshLayout
        mSrlSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新加载数据
                mMovieListPresenter.loadMovieList();

            }
        });

        mAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>(),
                R.layout.item_list_movie);
        mLvListView.setAdapter(mAdapter);
        mLvListView.setFooterText("正在加载更多影视...");     //设置加载更多文字
        mLvListView.setLoadMoreListener(this);              //设置加载更多监听
        mLvListView.setOnItemClickListener(this);           //设置单击Item事件
        //加载初始数据
        mMovieListPresenter.loadMovieList();
    }

    @Override
    public void onLoadMore() {
        //ListView加载更多的回调
        //获取更多数据
        String lastId = mMovieList.get(mMovieList.size() - 1).getId();
        mMovieListPresenter.loadMore(lastId);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String itemId = mMovieList.get(position).getItemId();
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("item_id", itemId);
        startActivity(intent);
    }

    @Override
    public void setMovieList(final List<Movie> movieList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在UI线程更新DataList
                mAdapter.setDataList(movieList);
                mMovieList = movieList;
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
    public void addMovieList(final List<Movie> movieList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在UI线程更新DataList
                mMovieList.addAll(movieList);
                mAdapter.setDataList(mMovieList);
                mLvListView.setLoadCompleted();
            }
        });
    }
}
