package com.zf.weisport.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.VideoAdapter;
import com.zf.weisport.adapter.VideoListAdapter;
import com.zf.weisport.databinding.FragmentVideoShow11Binding;
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
public class VideoShowFragment11 extends BaseFragment<VideoViewModel,FragmentVideoShow11Binding>
implements IVideoCallback,PullToRefreshBase.OnRefreshListener2,AdapterView.OnItemClickListener{

    private static final String TYPE = "Type";
    private VideoListAdapter mAdapter;
    private List<GetVideoModel> mList;
    private VideoViewModel      mViewModel;

    public static VideoShowFragment11 newInstance(int position) {
        VideoShowFragment11 fragment = new VideoShowFragment11();
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
        return R.layout.fragment_video_show11;
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
            mAdapter.setDatas(mList);
        }
    }
    private void initView() {
        getBinding().refreshListview.setMode(PullToRefreshBase.Mode.BOTH);
        getBinding().refreshListview.setShowDividers(0);
        getBinding().refreshListview.getLoadingLayoutProxy().setTextTypeface(Typeface.DEFAULT);
        getBinding().refreshListview.getLoadingLayoutProxy().setRefreshingLabel("加载中...");
        getBinding().refreshListview.getLoadingLayoutProxy().setReleaseLabel("释放更新");
        getBinding().refreshListview.setOnRefreshListener(this);
        mAdapter = new VideoListAdapter();
        getBinding().refreshListview.setAdapter(mAdapter);
    }

    @Override
    public void onGetVideoSuccess() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mList = getViewModel().getmList();
        mAdapter.setDatas(mList);
    }

    @Override
    public void onGetMoreVideoStatus(boolean status,List<GetVideoModel> getVideoModels) {
      /*  if (status){
            mList.addAll(mList.size(),getVideoModels);
            mAdapter.addAll(mList.size(),getVideoModels);
        }*/
    }

    @Override
    public void onNetEmpty() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onNetError() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        /*getViewModel().setPageIndex(1);
        getViewModel().getVideo();*/
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
