package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.ui.fragment.base.ToolbarBaseFragment;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @version V1.0 <广场 界面>
 * @author: Xs
 * @date: 2016-09-08 17:24
 * @email Xs.lin@foxmail.com
 */
public class SquareFragment extends ToolbarBaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    MultiStateView      multiStateView;
    BGARefreshLayout    mBGARefreshLayout;
    private RecyclerView mRecyclerView;
//    private TopicCommentAdapter mAdapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_square;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getToolbar().setTitle(R.string.hot_square_title_text);
        getToolbar().setOnMenuItemClickListener(this);
        multiStateView = (MultiStateView) view.findViewById(R.id.multi_state_view);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        mBGARefreshLayout = (BGARefreshLayout) view.findViewById(R.id.bgaRefreshLayout);
        mBGARefreshLayout.setDelegate(this);
        mBGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        mBGARefreshLayout.setPullDownRefreshEnable(false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
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
}
