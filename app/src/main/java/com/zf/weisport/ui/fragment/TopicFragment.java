package com.zf.weisport.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @version V1.0 <话题>
 * @author: Xs
 * @date: 2016-09-12 16:34
 * @email Xs.lin@foxmail.com
 */
public class TopicFragment extends BaseFragment<TopicViewModel,FragmentTopicBinding> implements BGARefreshLayout.BGARefreshLayoutDelegate,ITopicCallback{

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
        EventBus.getDefault().register(this);
        initView();
        int Type = getArguments().getInt(EXTRA_TYPE, 1);
        String Obj_ID = getArguments().getString(EXTRA_ID);
        getViewModel().setType(Type);
        getViewModel().setObjType(Obj_ID);
        getViewModel().getTopicList();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getViewModel().getTopicList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getViewModel().getMoreTopicList();
        return true;
    }

    private void initView() {
        mAdapter = new TopicAdapter(getActivity(), (view1, getVideoModel, position) -> {

        });
        getBinding().recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        getBinding().recyclerview.setAdapter(mAdapter);

        getBinding().bgaRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        viewHolder.setLoadingMoreText(getString(R.string.loading_more_text));
        getBinding().bgaRefreshLayout.setRefreshViewHolder(viewHolder);

    }

    @Override
    public void onGetTopicSuccess(List<TopicModel> topicModels) {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        onFinishRefreshView();
        mAdapter.setData(topicModels);
    }

    @Override
    public void onGetMoreTopicSuccess(List<TopicModel> topicModels) {
        onFinishRefreshView();
        if (topicModels != null)
            mAdapter.addAll(topicModels);
    }

    @Override
    public void onNetEmpty() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        onFinishRefreshView();
    }

    @Override
    public void onNetError() {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        onFinishRefreshView();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * onEvent 用于接收评论区评论数目的变更
     * @param event
     */
    @Subscribe
    public void onEvent(TopicCommentFragment.TopicCommentCountEvent event) {
        TopicModel model = mAdapter.getItem(event.position);
        model.setComment(String.valueOf(event.commentCount));
        mAdapter.changeItem(event.position,model);
    }

}
