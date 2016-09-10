package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.VideoAdapter;
import com.zf.weisport.databinding.FragmentVideoShowBinding;
import com.zf.weisport.model.GetVideoModel;
import com.zf.weisport.ui.callback.IVideoCallback;
import com.zf.weisport.ui.fragment.base.BaseFragment;
import com.zf.weisport.ui.viewmodel.VideoViewModel;
import com.zf.widget.recycleview.DividerItemDecoration;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 15:36
 * @email Xs.lin@foxmail.com
 */
public class VideoShowFragment extends BaseFragment<VideoViewModel,FragmentVideoShowBinding>
implements BGARefreshLayout.BGARefreshLayoutDelegate,IVideoCallback{

    private static final String TYPE = "Type";
    private VideoAdapter        mAdapter;
    private List<GetVideoModel> mList;
    private int                 _pageIndex;

    public static VideoShowFragment newInstance(int position) {
        VideoShowFragment fragment = new VideoShowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected VideoViewModel initViewModel() {
        return new VideoViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setVideoViewModel(getViewModel());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_video_show;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        int type = getArguments().getInt(TYPE);
        Log.e("info", "onViewCreated: "+type );
        getViewModel().setType(type);
        getViewModel().setPageIndex(_pageIndex);
        if (mList == null) {
            Log.e("info", "onViewCreated: mlist == null" );
            getViewModel().setPageIndex(1);
            getViewModel().getVideo();
            getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        } else {
            Log.e("info", "onViewCreated: " +getViewModel().getType()+ "  "+getViewModel().getPageIndex());
            mAdapter.setData(mList);
        }
    }

    private void initView() {
        getBinding().bgaRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(getActivity(),true);
        viewHolder.setLoadingMoreText("加载更多...");
        getBinding().bgaRefreshLayout.setRefreshViewHolder(viewHolder);
        getBinding().bgaRefreshLayout.setPullDownRefreshEnable(false);

        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        getBinding().recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new VideoAdapter(getActivity(), (view, getVideoModel, position) -> {

        });
        getBinding().recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onGetVideoSuccess() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mList = getViewModel().getmList();
        mAdapter.setData(mList);
    }

    @Override
    public void onNetEmpty() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onNetError() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
