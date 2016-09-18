package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kennyc.view.MultiStateView;
import com.zf.weisport.R;
import com.zf.weisport.adapter.MyFollowListAdapter;
import com.zf.weisport.databinding.FragmentFollowBinding;
import com.zf.weisport.manager.util.PinyinComparator;
import com.zf.weisport.model.MyFollowListModel;
import com.zf.weisport.ui.callback.IFollowCallback;
import com.zf.weisport.ui.fragment.base.BaseFragment;
import com.zf.weisport.ui.viewmodel.FollowViewModel;
import com.zf.widget.sortlistview.CharacterParser;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 14:53
 * @email Xs.lin@foxmail.com
 */
public class FollowFragment extends BaseFragment<FollowViewModel,FragmentFollowBinding> implements IFollowCallback {

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator    pinyinComparator;
    //汉字转换成拼音的类
    private CharacterParser     characterParser;
    private MyFollowListAdapter mSortAdapter;

    @Override
    protected FollowViewModel initViewModel() {
        return new FollowViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setFollowViewModel(getViewModel());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_follow;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getViewModel().getFollowFriendsList();
    }

    private void initView() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        getBinding().sideBar.setTextView(getBinding().dialog);

        //设置右侧触摸监听
        getBinding().sideBar.setOnTouchingLetterChangedListener(s -> {
            //该字母首次出现的位置
            int position = mSortAdapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                getBinding().followList.setSelection(position);
            }

        });
        getBinding().followList.setOnItemClickListener((parent, view, position, id) -> {
            //这里要利用adapter.getItem(position)来获取当前position所对应的对象
        });
        mSortAdapter = new MyFollowListAdapter();
        getBinding().followList.setAdapter(mSortAdapter);
    }

    @Override
    public void onGetFollowFriendSuccess(List<MyFollowListModel> followListModels) {
        getBinding().multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mSortAdapter.updateListView(followListModels);
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
