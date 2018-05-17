package com.nullptr.one.activity;

import android.os.Bundle;
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
import com.nullptr.one.R;
import com.nullptr.one.adapter.ViewPagerAdapter;
import com.nullptr.one.fragment.ArticleListFragment;
import com.nullptr.one.fragment.MovieListFragment;
import com.nullptr.one.fragment.MusicListFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/7
 * @DESCRIPTION 主界面，三个Fragment分别表示文章 音乐 影视三个板块
 */
public class MainActivity extends BaseActivity {

    List<Fragment> mFragments = null;
    private DrawerLayout mDlDrawerLayout;
    private NavigationView mNvNavigationView;
    private TabLayout mTlTabLayout;
    private ViewPager mVpViewPager;

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

        FragmentManager fm = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fm);
        adapter.setFragments(mFragments);
        mVpViewPager.setAdapter(adapter);

        //使TabLayout ViewPager NavigationView相关联
        mTlTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(
                mVpViewPager));  //TabLayout随ViewPager变动
        mVpViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                mTlTabLayout));    //ViewPager随TabLayout变动
        mTlTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mNvNavigationView.setCheckedItem(R.id.nav_article);
                        break;
                    case 1:
                        mNvNavigationView.setCheckedItem(R.id.nav_music);
                        break;
                    case 2:
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
        });               //NavigationView随TabLayout变动
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
                                mTlTabLayout.getTabAt(0).select();
                                //跳转到文章Fragment
                                break;
                            case R.id.nav_music:
                                //选中音乐Tab
                                mTlTabLayout.getTabAt(1).select();
                                //跳转到音乐Fragment
                                break;
                            case R.id.nav_video:
                                //选中影视Tab
                                mTlTabLayout.getTabAt(2).select();
                                //跳转到视频Fragment
                                break;
                            default:
                        }
                        mDlDrawerLayout.closeDrawers();   //按下后同时关闭抽屉
                        return true;
                    }
                });
    }

    //加载数据
    @Override
    protected void loadData() {
    }


    //按下Toolbar上的东西时
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

    public Toolbar initToolbar(CharSequence title) {
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