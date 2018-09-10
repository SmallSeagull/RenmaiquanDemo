package com.dolphin.renmaicircle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Dolphin on 2018/8/14.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> tabTitle;
    private List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<String> tabTitle, List<Fragment> fragments) {
        super(fm);
        this.tabTitle = tabTitle;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }

}