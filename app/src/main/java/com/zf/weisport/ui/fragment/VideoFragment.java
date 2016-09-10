package com.zf.weisport.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zf.weisport.R;
import com.zf.weisport.adapter.VideoFragmentPagerAdapter;
import com.zf.weisport.ui.fragment.base.ToolbarBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 15:22
 * @email Xs.lin@foxmail.com
 */
public class VideoFragment extends ToolbarBaseFragment {

    @Bind(R.id.vp_video)
    ViewPager mViewPager;
    @Bind(R.id.tab_video)
    TabLayout mTabLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_video;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        VideoFragmentPagerAdapter mAdapter = new VideoFragmentPagerAdapter(getChildFragmentManager());
        String[] stringArray = view.getResources().getStringArray(R.array.videotypes);
        mAdapter.setTabs(stringArray);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);

    }

    @Override
    public void onResume() {
        super.onResume();
        getToolbar().setTitle(R.string.mainTab_video);
    }

}
