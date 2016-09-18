package com.zf.weisport.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.TopicAdapter;
import com.zf.weisport.databinding.FragmentTopicBinding;
import com.zf.weisport.model.TopicModel;
import com.zf.weisport.ui.callback.ITopicCallback;
import com.zf.weisport.ui.fragment.base.BaseFragment;
import com.zf.weisport.ui.viewmodel.TopicViewModel;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * @version V1.0 <话题>
 * @author: Xs
 * @date: 2016-09-12 16:34
 * @email Xs.lin@foxmail.com
 */
public class TopicFragment extends BaseFragment<TopicViewModel,FragmentTopicBinding> implements BGARefreshLayout.BGARefreshLayoutDelegate,ITopicCallback{

    private MultiStateView mMultiStateView;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private static final String EXTRA_TYPE = "type";//请求类型（1-标签 2-收藏 3-用户发布）
    private static final String EXTRA_ID = "id";//对应Type需要的ID
    public TopicFragment() {
    }

    public static TopicFragment newInstance(Context context, String id, int type) {

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, id);
        bundle.putInt(EXTRA_TYPE, type);
        return (TopicFragment) Fragment.instantiate(context, TopicFragment.class.getName(), bundle);
    }

    @Override
    protected TopicViewModel initViewModel() {
        return new TopicViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setTopicViewModel(getViewModel());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_topic;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null || !getArguments().containsKey(EXTRA_ID)) {
            throw new NullPointerException();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        int Type = getArguments().getInt(EXTRA_TYPE, 1);
        String Obj_ID = getArguments().getString(EXTRA_ID);
        getViewModel().setType(Type);
        getViewModel().setObjType(Obj_ID);
        getViewModel().getTopicList();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return true;
    }

    private void initView(View view) {
        mAdapter = new TopicAdapter(getActivity(), (view1, getVideoModel, position) -> {

        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.bgaRefreshLayout);
        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        viewHolder.setLoadingMoreText("加载更多....");
        mRefreshLayout.setRefreshViewHolder(viewHolder);

        mMultiStateView = (MultiStateView) view.findViewById(R.id.multi_state_view);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void onGetTopicSuccess(List<TopicModel> topicModels) {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mAdapter.setData(topicModels);
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
