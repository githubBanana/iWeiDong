package com.zf.weisport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zf.widget.carousel.AutoScrollViewPager;
import com.zf.widget.carousel.SquaViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-09 09:50
 * @email Xs.lin@foxmail.com
 */
public class TestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "TestActivity";

    AutoScrollViewPager mViewPager ;
    SquaViewPagerAdapter mAdapter;
    LinearLayout mPointer;
    List<HashMap<String,Object>> mList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_head_layout);

        View headView = getLayoutInflater().inflate(R.layout.carousel_layout,null,false);
        final LinearLayout addTextView = (LinearLayout) findViewById(R.id.testAdd);
        addTextView.addView(headView);

        mViewPager = (AutoScrollViewPager) headView.findViewById(R.id.autoScrollViewPager);
        mPointer = (LinearLayout) headView.findViewById(R.id.point_container);
        mAdapter = new SquaViewPagerAdapter();

        mList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap();
        map.put("filePath","http://weidongzn.com/Files/upload/201602/26/201602261409463148.jpg");
        HashMap<String,Object> map2 = new HashMap();
        map2.put("filePath","http://weidongzn.com/Files/upload/201602/26/money.jpg");
        HashMap<String,Object> map3 = new HashMap();
        map3.put("filePath","http://weidongzn.com//Files/upload/201605/24/i20160524140517832.jpg");
        mList.add(map);
        mList.add(map2);
        mList.add(map3);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(40,40);
        params.leftMargin=20;
        mPointer.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.navi_pic);
            mPointer.addView(imageView,params);
        }

        mAdapter.setStrings(mList,this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.startAutoScroll();
        mViewPager.setAutoScrollDurationFactor(20.0);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int mCurrentItem = position % mList.size();
        dotChange(mCurrentItem);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void dotChange(int currentItem) {
        int childCount = mPointer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mPointer.getChildAt(i).setEnabled(i != currentItem);
        }
    }
}
