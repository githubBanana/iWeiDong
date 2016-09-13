package com.zf.weisport.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.VideoListAdapter;
import com.zf.weisport.databinding.FragmentVideoShowBinding;
import com.zf.weisport.model.GetVideoModel;
import com.zf.weisport.ui.activity.WebActivity;
import com.zf.weisport.ui.callback.IVideoCallback;
import com.zf.weisport.ui.fragment.base.BaseFragment;
import com.zf.weisport.ui.viewmodel.VideoViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @version V1.0 <微视频 界面>
 * @author: Xs
 * @date: 2016-09-10 15:36
 * @email Xs.lin@foxmail.com
 */
public class VideoShowFragment extends BaseFragment<VideoViewModel,FragmentVideoShowBinding>
implements IVideoCallback,PullToRefreshBase.OnRefreshListener2,AdapterView.OnItemClickListener{

    private static final String TYPE = "Type";
    private VideoListAdapter mAdapter;
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
            mAdapter.setDatas(mList);
        }
    }

    private void initView() {
        getBinding().refreshListview.setMode(PullToRefreshBase.Mode.BOTH);
        getBinding().refreshListview.setShowDividers(0);
        getBinding().refreshListview.getLoadingLayoutProxy().setTextTypeface(Typeface.MONOSPACE);
        getBinding().refreshListview.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.mipmap.load_image_1));
        getBinding().refreshListview.setOnRefreshListener(this);
        getBinding().refreshListview.setOnItemClickListener(this);
        mAdapter = new VideoListAdapter();
        getBinding().refreshListview.setAdapter(mAdapter);
    }

    @Override
    public void onGetVideoSuccess() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        finishRefreshView();
        mList = getViewModel().getmList();
        mAdapter.setDatas(mList);
    }

    @Override
    public void onGetMoreVideoStatus(boolean status,List<GetVideoModel> getVideoModels) {
        if (status){
            mList.addAll(mList.size(),getVideoModels);
            mAdapter.addDatas(getVideoModels);
        }
        finishRefreshView();
    }

    @Override
    public void onNetEmpty() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        finishRefreshView();
    }

    @Override
    public void onNetError() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        finishRefreshView();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getViewModel().setPageIndex(1);
        getViewModel().getVideo();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (getViewModel().isCanPullUp())
            getViewModel().getMoreVideo();
        else
           finishRefreshView();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        GetVideoModel item = mAdapter.getItem(i-1);
        String url = item.url;
        WebActivity.start(getActivity(),url,WebActivity.WEB_VIDEO);
    }

    private void finishRefreshView() {
        //立即停止会导致 动画不能完结
        Observable.just("").delay(300, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            if(getBinding().refreshListview.isRefreshing()) {
                getBinding().refreshListview.onRefreshComplete();
            }
            finishRefreshView();
        });

    }
}
