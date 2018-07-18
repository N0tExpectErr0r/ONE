package com.nullptr.one.music.list;

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
import com.nullptr.one.base.BaseAdapter.OnItemClickListener;
import com.nullptr.one.base.OnMoreScrollListener;
import com.nullptr.one.music.detail.MusicDetailActivity;
import com.nullptr.one.music.list.IMusicList.MusicListPresenter;
import com.nullptr.one.music.list.IMusicList.MusicListView;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION 音乐列表fragment
 */
public class MusicListFragment extends Fragment implements MusicListView, OnItemClickListener {

    private RecyclerView mRvList;
    private SwipeRefreshLayout mSrlSwipeRefreshLayout;
    private MusicListPresenter mMusicListPresenter;
    private MusicAdapter mAdapter;
    private List<Music> mMusicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_music, container, false);
        mMusicListPresenter = new MusicListPresenterImpl(this);
        mRvList = v.findViewById(R.id.music_rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRvList.setLayoutManager(manager);
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
            mAdapter = new MusicAdapter(new ArrayList<Music>(),
                    R.layout.item_list_music,10);
            mRvList.setAdapter(mAdapter);
            mRvList.setOnScrollListener(new OnMoreScrollListener(mRvList) {
                @Override
                public void onLoadMore() {
                    //获取更多数据
                    String lastId = mMusicList.get(mMusicList.size() - 1).getId();
                    mMusicListPresenter.loadMore(lastId);
                }
            });              //设置加载更多监听
            mAdapter.setOnItemClickListener(this);           //设置单击Item事件
            //加载初始数据
            mMusicListPresenter.loadList();
        }
    }


    @Override
    public void setMusicList(final List<Music> musicList) {
        mAdapter.setDatas(musicList);
        mMusicList = musicList;
    }

    @Override
    public void showError(final String errorMsg) {
        //网络出错的处理
        Toast.makeText(getActivity(),"网络出错，请检查网络设置",Toast.LENGTH_SHORT).show();
        mSrlSwipeRefreshLayout.setRefreshing(false);
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
        mAdapter.setDatas(mMusicList);
    }



    @Override
    public void onItemClick(View view, int position) {
        String itemId = mMusicList.get(position).getItemId();
        MusicDetailActivity.actionStart(getActivity(), itemId);
    }
}
