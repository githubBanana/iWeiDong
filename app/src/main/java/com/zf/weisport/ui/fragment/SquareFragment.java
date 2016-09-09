package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.BaseAdapter;
import com.zf.weisport.adapter.SquareAdapter;
import com.zf.weisport.model.LabelTopicModel;
import com.zf.weisport.ui.fragment.base.ToolbarBaseFragment;
import com.zf.widget.carousel.AutoScrollViewPager;
import com.zf.widget.carousel.SquaViewPagerAdapter;
import com.zf.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * @version V1.0 <广场 界面>
 * @author: Xs
 * @date: 2016-09-08 17:24
 * @email Xs.lin@foxmail.com
 */
public class SquareFragment extends ToolbarBaseFragment
        implements BGARefreshLayout.BGARefreshLayoutDelegate,ViewPager.OnPageChangeListener {

    MultiStateView      multiStateView;
    BGARefreshLayout    mBGARefreshLayout;
    private RecyclerView mRecyclerView;
    private SquareAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_square;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    private View view;
    private void initView(View view) {

        multiStateView = (MultiStateView) view.findViewById(R.id.multi_state_view);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mBGARefreshLayout = (BGARefreshLayout) view.findViewById(R.id.bgaRefreshLayout);
        mBGARefreshLayout.setDelegate(this);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        mBGARefreshLayout.setCustomHeaderView(headView,true);

/*        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(getActivity(),true);
        viewHolder.setLoadingMoreText("加载更多...");
        mBGARefreshLayout.setRefreshViewHolder(viewHolder);*/

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new SquareAdapter(getActivity(), (view1, labelTopicModel, position) -> {

        });
        List<LabelTopicModel> mList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            LabelTopicModel model = new LabelTopicModel();
            model.setID(String.valueOf(i));
            model.setName("name "+i);
            mList.add(model);
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(mList);

    }

    AutoScrollViewPager mViewPager ;
    SquaViewPagerAdapter mViewPagerAdapter;
    LinearLayout mPointer;
    List<HashMap<String,Object>> mList;
    View headView;
    private void initViewPager(View view) {
        headView = getActivity().getLayoutInflater().inflate(R.layout.carousel_layout,null,false);
      /*  final LinearLayout addTextView = (LinearLayout) findViewById(R.id.testAdd);
        addTextView.addView(headView);*/

        mViewPager = (AutoScrollViewPager) headView.findViewById(R.id.autoScrollViewPager);
        mPointer = (LinearLayout) headView.findViewById(R.id.point_container);
        mViewPagerAdapter = new SquaViewPagerAdapter();

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
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.navi_pic);
            mPointer.addView(imageView,params);
        }

        mViewPagerAdapter.setStrings(mList,getActivity());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.startAutoScroll();
        mViewPager.setAutoScrollDurationFactor(20.0);


    }
    @Override
    public void onResume() {
        super.onResume();
        getToolbar().setTitle(R.string.hot_square_title_text);
        getToolbar().setOnMenuItemClickListener(this);
        initViewPager(view);
        initView(view);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.square_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_pic_photo_id:
                showToast("hello");
                break;
        }
        return true;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
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
