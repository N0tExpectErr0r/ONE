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
import com.nullptr.one.view.adapter.MusicAdapter;
import com.nullptr.one.bean.Music;
import com.nullptr.one.presenter.MusicListPresenterImpl;
import com.nullptr.one.presenter.interfaces.ListPresenter;
import com.nullptr.one.view.ui.LoadMoreListView;
import com.nullptr.one.view.activity.MusicDetailActivity;
import com.nullptr.one.view.interfaces.IListView.MusicListView;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐列表fragment
 */
public class MusicListFragment extends Fragment implements MusicListView,
        ListView.OnItemClickListener, LoadMoreListView.OnLoadMoreListener {

    private LoadMoreListView mLvListView;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private ListPresenter mMusicListPresenter;
    private MusicAdapter mAdapter;
    private List<Music> mMusicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_music, container, false);
        mMusicListPresenter = new MusicListPresenterImpl(this);
        mLvListView = v.findViewById(R.id.music_lv_listview);
        mSrlSwipeRefreshLayout = v.findViewById(R.id.music_srl_swipe_refresh);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        //在onStart中初始化原因同ArticleListFragment

        //初始化SwipeRefreshLayout
        mSrlSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新 加载初始数据
                mMusicListPresenter.loadList();

            }
        });

        if (mMusicList == null || mMusicList.size() == 0) {
            //不是每次都要刷新的，之前有数据的时候不需要刷新
            mAdapter = new MusicAdapter(getActivity(), new ArrayList<Music>(),
                    R.layout.item_list_music);
            mLvListView.setAdapter(mAdapter);
            mLvListView.setFooterText("正在加载更多音乐...");     //设置加载更多文字
            mLvListView.setLoadMoreListener(this);              //设置加载更多监听
            mLvListView.setOnItemClickListener(this);           //设置单击Item事件
            //加载初始数据
            mMusicListPresenter.loadList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemId = mMusicList.get(position).getItemId();
        Intent intent = new Intent(MusicDetailActivity.ACTION);
        intent.putExtra("item_id", itemId);
        startActivity(intent);
    }

    @Override
    public void setMusicList(final List<Music> musicList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在UI线程更新DataList
                mAdapter.setDataList(musicList);
                mMusicList = musicList;
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
    public void addMusicList(final List<Music> musicList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在UI线程更新DataList
                mMusicList.addAll(musicList);
                mAdapter.setDataList(mMusicList);
                mLvListView.setLoadCompleted();
            }
        });
    }

    @Override
    public void onLoadMore() {
        //ListView加载更多的回调
        //获取更多数据
        String lastId = mMusicList.get(mMusicList.size() - 1).getId();
        mMusicListPresenter.loadMore(lastId);
    }
}