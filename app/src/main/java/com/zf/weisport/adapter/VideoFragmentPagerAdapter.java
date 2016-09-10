package com.zf.weisport.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zf.weisport.ui.fragment.VideoShowFragment;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 15:34
 * @email Xs.lin@foxmail.com
 */
public class VideoFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] _Titles;

    public VideoFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return VideoShowFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        if (_Titles!=null)return _Titles.length;
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _Titles[position];
    }

    public void setTabs(String[] stringArray) {
        _Titles = stringArray;
    }
}
