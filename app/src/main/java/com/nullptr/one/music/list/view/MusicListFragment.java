package com.nullptr.one.music.list.view;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
import com.nullptr.one.bean.Music;
import com.nullptr.one.main.view.MainActivity;
import com.nullptr.one.music.detail.view.MusicDetailActivity;
import com.nullptr.one.music.list.IMusicList.MusicListPresenter;
import com.nullptr.one.music.list.IMusicList.MusicListView;
import com.nullptr.one.music.list.adapter.MusicAdapter;
import com.nullptr.one.music.list.presenter.MusicListPresenterImpl;
import com.nullptr.one.ui.LoadMoreListView;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐列表fragment
 */
public class MusicListFragment extends Fragment implements MusicListView,
        ListView.OnItemClickListener, LoadMoreListView.OnLoadMoreListener {

    private final int ID = 3;
    private NotificationManager mManager;
    private LoadMoreListView mLvListView;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private MusicListPresenter mMusicListPresenter;
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
                mMusicListPresenter.updateList();
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
        MusicDetailActivity.actionStart(getActivity(), itemId);
    }

    @Override
    public void setMusicList(final List<Music> musicList) {
        mAdapter.setDataList(musicList);
        mMusicList = musicList;
    }

    @Override
    public void showError(final String errorMsg) {
        //网络出错的处理
        Toast.makeText(getActivity(),"网络出错，请检查网络设置",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mSrlSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {

        mSrlSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addMusicList(final List<Music> musicList) {
        mMusicList.addAll(musicList);
        mAdapter.setDataList(mMusicList);
        mLvListView.setLoadCompleted();
    }

    @Override
    public void onLoadMore() {
        //ListView加载更多的回调
        //获取更多数据
        String lastId = mMusicList.get(mMusicList.size() - 1).getId();
        mMusicListPresenter.loadMore(lastId);
    }


}
