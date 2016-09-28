package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.SquareAdapter;
import com.zf.weisport.databinding.FragmentSquareBinding;
import com.zf.weisport.manager.util.UserUtil;
import com.zf.weisport.model.LabelTopicModel;
import com.zf.weisport.ui.activity.AccountActivity;
import com.zf.weisport.ui.activity.CreateTopicActivity;
import com.zf.weisport.ui.activity.TopicActivity;
import com.zf.weisport.ui.activity.WebActivity;
import com.zf.weisport.ui.callback.ISquareCallback;
import com.zf.weisport.ui.fragment.base.ToolbarBaseFragment;
import com.zf.weisport.ui.viewmodel.SquareViewModel;
import com.zf.widget.carousel.AutoScrollViewPager;
import com.zf.widget.carousel.SquaViewPagerAdapter;
import com.zf.widget.recycleview.DividerItemDecoration;

import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @version V1.0 <广场 界面>
 * @author: Xs
 * @date: 2016-09-08 17:24
 * @email Xs.lin@foxmail.com
 */
public class SquareFragment extends ToolbarBaseFragment<SquareViewModel,FragmentSquareBinding>
        implements BGARefreshLayout.BGARefreshLayoutDelegate,ViewPager.OnPageChangeListener,ISquareCallback,SquaViewPagerAdapter.OnListenViewPagerClick {

    private SquareAdapter                   mAdapter;
    private AutoScrollViewPager             mViewPager ;
    private SquaViewPagerAdapter            mViewPagerAdapter;
    private LinearLayout                    mPointer;
    private List<HashMap<String,Object>>    mTopList;
    private List<LabelTopicModel>           mLabelTopicList;

    @Override
    protected SquareViewModel initViewModel() {
        return new SquareViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setSquareViewModel(getViewModel());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_square;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        if (mTopList == null) {
            getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            new Handler().post(() -> getViewModel().getTop());
        } else
            setDataToViewPager();

        if (mLabelTopicList == null)
            new Handler().post(() -> getViewModel().getLabelTopic());
        else
            mAdapter.setData(mLabelTopicList);

    }
    private void initView() {

        final View headView = getActivity().getLayoutInflater().inflate(R.layout.carousel_layout,null,false);
        mViewPager = (AutoScrollViewPager) headView.findViewById(R.id.autoScrollViewPager);
        mPointer = (LinearLayout) headView.findViewById(R.id.point_container);
        mViewPagerAdapter = new SquaViewPagerAdapter();

        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        getBinding().bgaRefreshLayout.setDelegate(this);
        getBinding().bgaRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        getBinding().bgaRefreshLayout.setCustomHeaderView(headView,true);
//        getBinding().bgaRefreshLayout.setPullDownRefreshEnable(false);

        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        getBinding().recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new SquareAdapter(getActivity(), (view1, labelTopicModel, position) -> {
            if (UserUtil.isLogin(getActivity())) {
                String id = mLabelTopicList.get(position).getID();
                String title = mLabelTopicList.get(position).getName();
                TopicActivity.start(getActivity(), id, title);
            } else {
                AccountActivity.start(getActivity());
            }
        });
        getBinding().recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getToolbar().setTitle(R.string.hot_square_title_text);
        getToolbar().setOnMenuItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.square_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.item_pic_photo_id)
            if (UserUtil.isLogin(getActivity()))
                CreateTopicActivity.start(getActivity());
            else
                AccountActivity.start(getActivity());
        return super.onMenuItemClick(item);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getViewModel().getTop();
        getViewModel().getLabelTopic();
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
        int mCurrentItem = position % mTopList.size();
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

    @Override
    public void onGetLableTopicSuccess() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        dismissRefreshingView();
        mLabelTopicList = getViewModel().getLabelTopicList();
        mAdapter.setData(mLabelTopicList);
    }

    @Override
    public void onGetTopSuccess() {
        mTopList = getViewModel().getTopList();
        setDataToViewPager();
    }

    private void setDataToViewPager() {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(40,40);
        params.leftMargin=20;
        mPointer.removeAllViews();
        for (int i = 0; i < mTopList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.navi_pic);
            mPointer.addView(imageView,params);
        }

        mViewPagerAdapter.setStrings(mTopList,this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.startAutoScroll();
        mViewPager.setAutoScrollDurationFactor(20.0);
    }

    @Override
    public void onNetEmpty() {
        dismissRefreshingView();
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onNetError() {
        dismissRefreshingView();
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    private void dismissRefreshingView() {
        if (getBinding().bgaRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING)
            getBinding().bgaRefreshLayout.endRefreshing();
    }

    @Override
    public void onListenViewPagerItemClick(HashMap<String, Object> map) {
        String url = (String) map.get("url");
        WebActivity.start(getActivity(),url,WebActivity.WEB_VIDEO);

    }
}
