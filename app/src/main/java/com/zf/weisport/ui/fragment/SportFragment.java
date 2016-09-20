package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zf.weisport.R;
import com.zf.weisport.adapter.ViewPaperAdapter;
import com.zf.weisport.manager.util.AnimatorUtil;
import com.zf.weisport.ui.fragment.base.BaseFragment;
import com.zf.widget.ControlViewPaper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version V1.0 <Sport 导航界面>
 * @author: Xs
 * @date: 2016-09-08 15:26
 * @email Xs.lin@foxmail.com
 */
public class SportFragment extends BaseFragment
        implements ViewPager.OnPageChangeListener,View.OnTouchListener {

    @Bind(R.id.iv_1)                ImageView           mImg1;
    @Bind(R.id.iv_2)                ImageView           mImg2;
    @Bind(R.id.iv_3)                ImageView           mImg3;
    @Bind(R.id.iv_4)                ImageView           mImg4;
    @Bind(R.id.tv_entry)            TextView            mEntry;
    @Bind(R.id.sport_viewPaper)     ControlViewPaper    viewPager ;

    private List<ImageView>         _ivs = new ArrayList<>();
    private ArrayList<View>         _viewList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sport;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        /**
         * init view
         */
        _ivs.add(mImg1);
        _ivs.add(mImg2);
        _ivs.add(mImg3);
        _ivs.add(mImg4);
        _ivs.get(0).setEnabled(false);

        LayoutInflater mInflater = getActivity().getLayoutInflater();
        _viewList = new ArrayList<>();
        _viewList.add(mInflater.inflate(R.layout.layout1_sport,null));
        _viewList.add(mInflater.inflate(R.layout.layout2_sport,null));
        _viewList.add(mInflater.inflate(R.layout.layout3_sport,null));
        _viewList.add(mInflater.inflate(R.layout.layout4_sport,null));

        viewPager.setAdapter(new ViewPaperAdapter(_viewList));
        viewPager.setOnPageChangeListener(this);
        viewPager.setDisableScroll(true);
        mEntry.setOnTouchListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        dotChange(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void dotChange(int index){
        for (int i = 0; i < _ivs.size(); i++) {
            if (i == index) {
                _ivs.get(i).setEnabled(false);
            }else {
                _ivs.get(i).setEnabled(true);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.tv_entry) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimatorUtil.play(view,true);
                    break;
                case MotionEvent.ACTION_UP:
                    AnimatorUtil.play(view,false);
                    break;
            }
        }
        return true;
    }
}
