package com.zf.weisport.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.TopicCommentAdapter;
import com.zf.weisport.databinding.FragmentTopicCommentBinding;
import com.zf.weisport.model.TopicCommentModel;
import com.zf.weisport.ui.callback.ITopicCommentCallback;
import com.zf.weisport.ui.fragment.base.BaseFragment;
import com.zf.weisport.ui.viewmodel.TopicCommentViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @version V1.0 <话题评论区>
 * @author: Xs
 * @date: 2016-09-19 10:38
 * @email Xs.lin@foxmail.com
 */
public class TopicCommentFragment extends BaseFragment<TopicCommentViewModel,FragmentTopicCommentBinding>
        implements ITopicCommentCallback,BGARefreshLayout.BGARefreshLayoutDelegate{

    private static final String EXTRA_TOPIC_ID = "topicId";
    private static int _position;//为了把评论区的评论数目更新到topic主页面对应的item上

    private TopicCommentAdapter mAdapter;
    public TopicCommentFragment() {
        // Required empty public constructor
    }

    public static TopicCommentFragment newInstance(Context context, String topicId,int position) {
        _position = position;
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TOPIC_ID, topicId);
        return (TopicCommentFragment) Fragment.instantiate(context, TopicCommentFragment.class.getName(), bundle);
    }

    @Override
    protected TopicCommentViewModel initViewModel() {
        return new TopicCommentViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setTopicCommentViewModel(getViewModel());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_topic_comment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null || !getArguments().containsKey(EXTRA_TOPIC_ID)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        String topicId = getArguments().getString(EXTRA_TOPIC_ID);
        getViewModel().setTopicId(topicId);
        getViewModel().getTopicCommentList();
    }

    private void initView() {
        mAdapter = new TopicCommentAdapter();
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        getBinding().recyclerView.setAdapter(mAdapter);

        getBinding().bgaRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        viewHolder.setLoadingMoreText(getString(R.string.loading_more_text));
        getBinding().bgaRefreshLayout.setRefreshViewHolder(viewHolder);
        getBinding().etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String inputContent = charSequence.toString();
                if (inputContent.length() <= 100)
                    getViewModel().setCommentContent(charSequence.toString());
                else
                    showToast(R.string.wordlimit_100);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        getBinding().ivSend.setOnClickListener(view -> getViewModel().releaseComment());
    }


    @Override
    public void onGetCommentListSuccess(List<TopicCommentModel> topicCommentModels) {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        onFinishRefreshView();
        mAdapter.setData(topicCommentModels);
        /**
         * 将 评论数目更新到 TopicFragment 话题主页面上
         */
        EventBus.getDefault().post(new TopicCommentCountEvent(_position,getViewModel().getRealCommentCount()));
    }

    @Override
    public void onGetMoreCommentListSuccess(List<TopicCommentModel> topicCommentModels) {
        onFinishRefreshView();
        if (topicCommentModels != null)
            mAdapter.addAll(topicCommentModels);
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
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getViewModel().getTopicCommentList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (getViewModel().getRealCommentCount() > 8) {//当评论数达到8以上时，则允许上拉刷新
            getViewModel().getMoreTopicCommentList();
            return true;
        }
        return false;
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

    /**
     * eventbus 评论数目 传输事件
     */
    public class TopicCommentCountEvent {
        int position;
        int commentCount;
        public TopicCommentCountEvent(int position,int commentCount) {
            this.position = position;
            this.commentCount = commentCount;
        }
    }

}
