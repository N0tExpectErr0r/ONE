package com.nullptr.one.movie.list.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseAdapter.OnItemClickListener;
import com.nullptr.one.base.OnMoreScrollListener;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.movie.detail.view.MovieDetailActivity;
import com.nullptr.one.movie.list.IMovieList.MovieListPresenter;
import com.nullptr.one.movie.list.IMovieList.MovieListView;
import com.nullptr.one.movie.list.adapter.MovieAdapter;
import com.nullptr.one.movie.list.presenter.MovieListPresenterImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/17
 * @DESCRIPTION 电影列表fragment
 */
public class MovieListFragment extends Fragment implements OnItemClickListener, MovieListView {
    private RecyclerView mRvList;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private MovieListPresenter mMovieListPresenter;
    private MovieAdapter mAdapter;
    private List<Movie> mMovieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_movie, container, false);
        mMovieListPresenter = new MovieListPresenterImpl(this);
        mRvList = v.findViewById(R.id.movie_rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRvList.setLayoutManager(manager);
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
                mMovieListPresenter.updateList();
            }
        });
        if (mMovieList == null || mMovieList.size() == 0) {
            //不是每次都要刷新的，之前有数据的时候不需要刷新
            mAdapter = new MovieAdapter(new ArrayList<Movie>(),
                    R.layout.item_list_movie,10);
            mRvList.setAdapter(mAdapter);
            //设置加载更多监听
            mRvList.setOnScrollListener(new OnMoreScrollListener(mRvList) {
                @Override
                public void onLoadMore() {
                    //获取更多数据
                    String lastId = mMovieList.get(mMovieList.size() - 1).getId();
                    mMovieListPresenter.loadMore(lastId);
                }
            });
            mAdapter.setOnItemClickListener(this);           //设置单击Item事件
            //加载初始数据
            mMovieListPresenter.loadList();
        }
    }

    @Override
    public void setMovieList(final List<Movie> movieList) {
        //在UI线程更新DataList
        mAdapter.setDatas(movieList);
        mMovieList = movieList;
    }

    @Override
    public void showError(String errorMsg) {
        //网络出错的处理
        Toast.makeText(getActivity(),"网络出错，请检查网络设置",Toast.LENGTH_SHORT).show();
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
    public void addMovieList(final List<Movie> movieList) {

        //在UI线程更新DataList
        mMovieList.addAll(movieList);
        mAdapter.setDatas(mMovieList);
    }

    @Override
    public void onItemClick(View view, int position) {
        String itemId = mMovieList.get(position).getItemId();
        MovieDetailActivity.actionStart(getActivity(), itemId);
    }
}
