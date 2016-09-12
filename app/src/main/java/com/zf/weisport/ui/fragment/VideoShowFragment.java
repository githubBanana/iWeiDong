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

/**
 * @version V1.0 <微视频 界面>
 * @author: Xs
 * @date: 2016-09-10 15:36
 * @email Xs.lin@foxmail.com
 */
public class VideoShowFragment extends BaseFragment<VideoViewModel,FragmentVideoShowBinding>
implements BGARefreshLayout.BGARefreshLayoutDelegate,IVideoCallback{

    private static final String TYPE = "Type";
    private VideoAdapter        mAdapter;
    private List<GetVideoModel> mList;
    private VideoViewModel      mViewModel;

    public static VideoShowFragment newInstance(int position) {
        VideoShowFragment fragment = new VideoShowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected VideoViewModel initViewModel() {
        if (mViewModel == null)
            mViewModel = new VideoViewModel(this);
        return mViewModel;
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
        initData();

    }

    private void initData() {
        if (mList == null) {
            getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            getViewModel().setPageIndex(1);
            getViewModel().setType(getArguments().getInt(TYPE,0));
            getViewModel().getVideo();
        } else {
            getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            mAdapter.setData(mList);
        }
    }
    private void initView() {
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        getBinding().recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new VideoAdapter(getActivity(), (view1, getVideoModel, position) -> {
        });
        getBinding().recyclerView.setAdapter(mAdapter);
        getBinding().bgaRefreshLayoutVideo.setDelegate(this);
        getBinding().bgaRefreshLayoutVideo.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(),true));
        getBinding().bgaRefreshLayoutVideo.setIsShowLoadingMoreView(true);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getViewModel().setPageIndex(1);
        getViewModel().getVideo();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return true;
    }

    @Override
    public void onGetVideoSuccess() {
        Log.e("info", "onGetVideoSuccess: "+getBinding().bgaRefreshLayoutVideo.getCurrentRefreshStatus() );
        dismissRefreshingView();
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mList = getViewModel().getmList();
        mAdapter.setData(mList);
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
        if (getBinding().bgaRefreshLayoutVideo.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING)
            getBinding().bgaRefreshLayoutVideo.endRefreshing();
    }


}
