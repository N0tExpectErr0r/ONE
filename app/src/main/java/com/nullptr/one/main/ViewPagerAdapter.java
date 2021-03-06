package com.nullptr.one.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/7
 * @DESCRIPTION 主页的ViewPager的Adapter
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void setFragments(List<Fragment> fragments) {
        if (fragments == null || fragments.size() == 0) {
            throw new IllegalArgumentException("Fragment list is null or empty");
        }
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
