package com.nullptr.one.main.view;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.nullptr.one.R;
import com.nullptr.one.base.BaseActivity;
import com.nullptr.one.article.list.view.ArticleListFragment;
import com.nullptr.one.image.view.ImageDetailActivity;
import com.nullptr.one.main.adapter.ViewPagerAdapter;
import com.nullptr.one.movie.list.view.MovieListFragment;
import com.nullptr.one.music.list.view.MusicListFragment;
import com.nullptr.one.service.AutoUpdateService;
import com.nullptr.one.service.DownloadArticleService;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/7
 * @DESCRIPTION 主界面，三个Fragment分别表示文章 音乐 影视三个板块
 */
public class MainActivity extends BaseActivity {

    final private static int ARTICLE = 0;   //文章模块对应的Tab位置
    final private static int MUSIC = 1;     //音乐模块对应的Tab位置
    final private static int MOVIE = 2;     //电影模块对应的Tab位置

    private List<Fragment> mFragments = null;
    private DrawerLayout mDlDrawerLayout;
    private NavigationView mNvNavigationView;
    private FloatingActionButton mFabDownload;
    private TabLayout mTlTabLayout;
    private ViewPager mVpViewPager;
    private DownloadReceiver mDownloadReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        mDownloadReceiver = new DownloadReceiver();
        IntentFilter intentFilter = new IntentFilter();
        //设置接收广播的类型
        intentFilter.addAction("download");
        registerReceiver(mDownloadReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDownloadReceiver);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        //初始化Toolbar(带菜单)
        initToolbar("ONE · 一个");
        //初始化ViewPager以及TabLayout
        mVpViewPager = findViewById(R.id.main_vp_viewpager);
        mVpViewPager.setOffscreenPageLimit(2);
        mTlTabLayout = findViewById(R.id.main_tl_tab);

        //初始化ViewPager中的Fragments对应的List
        mFragments = new ArrayList<>();
        mFragments.add(new ArticleListFragment());
        mFragments.add(new MusicListFragment());
        mFragments.add(new MovieListFragment());

        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        adapter.setFragments(mFragments);
        mVpViewPager.setAdapter(adapter);

        //使TabLayout ViewPager NavigationView相关联
        mTlTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpViewPager));  //TabLayout随ViewPager变动
        mVpViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTlTabLayout));    //ViewPager随TabLayout变动
        //NavigationView随TabLayout变动
        mTlTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case ARTICLE:
                        mNvNavigationView.setCheckedItem(R.id.nav_article);
                        break;
                    case MUSIC:
                        mNvNavigationView.setCheckedItem(R.id.nav_music);
                        break;
                    case MOVIE:
                        mNvNavigationView.setCheckedItem(R.id.nav_video);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //初始化侧边栏
        mDlDrawerLayout = findViewById(R.id.main_dl_drawer);
        mNvNavigationView = findViewById(R.id.main_nav_view);
        mNvNavigationView.setCheckedItem(R.id.nav_article);
        mNvNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_article:
                                //选中文章Tab
                                mTlTabLayout.getTabAt(ARTICLE).select();
                                //跳转到文章Fragment
                                break;
                            case R.id.nav_music:
                                //选中音乐Tab
                                mTlTabLayout.getTabAt(MUSIC).select();
                                //跳转到音乐Fragment
                                break;
                            case R.id.nav_video:
                                //选中影视Tab
                                mTlTabLayout.getTabAt(MOVIE).select();
                                //跳转到视频Fragment
                                break;
                            case R.id.nav_image:
                                //选中图文Tab
                                //跳转到图文Activity
                                mNvNavigationView.setCheckedItem(R.id.nav_article);
                                ImageDetailActivity.actionStart(MainActivity.this);
                                break;
                            default:
                        }
                        mDlDrawerLayout.closeDrawers();   //按下后同时关闭抽屉
                        return true;
                    }
                });
        mFabDownload = findViewById(R.id.main_fab_download);
        mFabDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DownloadArticleService.class);
                startService(intent);
                mFabDownload.setEnabled(false);
            }
        });
    }

    class DownloadReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String actionType = intent.getStringExtra("action_type");
            switch (actionType){
                case "OVER":
                    mFabDownload.setEnabled(true);
                    break;
                default:
            }
        }
    }

    //加载数据
    @Override
    protected void loadData() {
        //TODO
        Intent intent = new Intent(this,AutoUpdateService.class);
        intent.putExtra("isStart",true);
        startService(intent);
    }


    //按下Toolbar上的图标时
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //菜单按钮,打开抽屉
                mDlDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private Toolbar initToolbar(CharSequence title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        return toolbar;
    }
}
