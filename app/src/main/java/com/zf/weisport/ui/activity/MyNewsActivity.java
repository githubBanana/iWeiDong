package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.MyNewsAdapter;
import com.zf.weisport.databinding.ActivityMynewsBinding;
import com.zf.weisport.model.MyMessageModel;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.IMyNewsCallback;
import com.zf.weisport.ui.viewmodel.MyNewsViewModel;
import com.zf.widget.recycleview.DividerItemDecoration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @version V1.0 <我的消息 界面>
 * @author: Xs
 * @date: 2016-09-18 09:07
 * @email Xs.lin@foxmail.com
 */
public class MyNewsActivity extends BaseActivity<MyNewsViewModel,ActivityMynewsBinding>
implements IMyNewsCallback,BGARefreshLayout.BGARefreshLayoutDelegate{

    private MyNewsAdapter mAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,MyNewsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected MyNewsViewModel initViewModel() {
        return new MyNewsViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setMyNewsViewModel(getViewModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynews,true);

        getBinding().bgaRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(this,true);
        viewHolder.setLoadingMoreText(getString(R.string.loading_more_text));
        getBinding().bgaRefreshLayout.setRefreshViewHolder(viewHolder);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        getBinding().recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new MyNewsAdapter(this, (view, myMessageModel, position) -> {
            getViewModel().readMessage(position,myMessageModel);
        });
        getBinding().recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.tv_mynews);
        getViewModel().getMyMessage();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getViewModel().setPageIndex(1);
        getViewModel().getMyMessage();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getViewModel().getMoreMyMessage();
        return true;
    }

    @Override
    public void onGetMyMessageSuccess(List<MyMessageModel> myMessageModels) {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.setData(myMessageModels);
    }

    @Override
    public void onGetMoreMyMessageSuccess(List<MyMessageModel> myMessageModels) {
        mAdapter.addAll(myMessageModels);
    }

    @Override
    public void onReadMessageSuccess(int position,MyMessageModel modifyMessageModel) {
        WebActivity.start(this,modifyMessageModel.getUrl(),WebActivity.WEB_NEWS);
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
    public void onFinishRefreshView() {
        Observable.just("").delay(300, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            if (getBinding().bgaRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING)
                getBinding().bgaRefreshLayout.endRefreshing();
            if (getBinding().bgaRefreshLayout.isLoadingMore())
                getBinding().bgaRefreshLayout.endLoadingMore();
        });
    }

}
