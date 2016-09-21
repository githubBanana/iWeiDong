package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zf.weisport.R;
import com.zf.weisport.adapter.ViewPaperAdapter;
import com.zf.weisport.manager.util.SPUtil;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.widget.ControlViewPaper;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <导航页>
 * @author: Xs
 * @date: 2016-09-21 14:55
 * @email Xs.lin@foxmail.com
 */
public class NavigationActivity extends BaseActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {

    private TextView            mTvLaunch;
    private ControlViewPaper    mViewPager;
    private ViewPaperAdapter    viewPaperAdapter;
    private List<View>          views;
    private int[]               imageIDs = {R.id.navi_img_1, R.id.navi_img_2,R.id.navi_img_3};
    private ImageView[]         mImageViews = new ImageView[imageIDs.length];
    private int                 curViewPaperIndex = 0;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,NavigationActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navigation);
        mViewPager = (ControlViewPaper) findViewById(R.id.navi_viewpager);
        init();
    }

    private void init() {
        LayoutInflater mInflater = getLayoutInflater().from(this);
        //获取 viewpaper top 布局资源
        for (int i = 0; i < imageIDs.length; i++) {
            mImageViews[i] = (ImageView) findViewById(imageIDs[i]);
            mImageViews[i].setEnabled(true);
            mImageViews[i].setTag(i);
        }
        View view1 = mInflater.inflate(R.layout.layout_navi_1,null);
        View view2 = mInflater.inflate(R.layout.layout_navi_2,null);
        View view3 = mInflater.inflate(R.layout.layout_navi_3,null);
        mTvLaunch = (TextView) view3.findViewById(R.id.tvlaunch);
        mTvLaunch.setOnClickListener(this);
        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

        viewPaperAdapter = new ViewPaperAdapter(views);
        mViewPager.setAdapter(viewPaperAdapter);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setOnPageChangeListener(this);
        //default setting
        mViewPager.setCurrentItem(curViewPaperIndex);
        mImageViews[curViewPaperIndex].setEnabled(false);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setViewPageIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    private void setViewPageIndex(int index) { //切换页
        if (index < 0) {
            index = 0;
        } else if (index > imageIDs.length - 1) {
            index = imageIDs.length - 1;
        }
        index = index % imageIDs.length;
        if (curViewPaperIndex != index) {
            mImageViews[curViewPaperIndex].setEnabled(true);
            curViewPaperIndex = index;
            mImageViews[curViewPaperIndex].setEnabled(false);
            mViewPager.setCurrentItem(curViewPaperIndex);
            setDisappear();
        }
    }
    private void setDisappear() {
        if(curViewPaperIndex == imageIDs.length - 1) {
//            viewPager.setDisableScroll(true);
            showImageViews(false);
        } else {
            showImageViews(true);
        }
    }

    private void showImageViews(boolean show) {
        int showInt;
        if (show)
            showInt = View.VISIBLE;
        else
            showInt = View.INVISIBLE;
        for (int i = 0; i < imageIDs.length; i++) {
            mImageViews[i].setVisibility(showInt);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvlaunch:
                SPUtil.saveIntData(NavigationActivity.this, "started", 1);
                MainActivity.start(this);
                finish();
                break;
        }
    }

}


