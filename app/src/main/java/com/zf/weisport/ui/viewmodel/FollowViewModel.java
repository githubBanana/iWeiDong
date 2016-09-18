package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.MyFollowListModel;
import com.zf.weisport.presenter.IFollowFriendsView;
import com.zf.weisport.presenter.biz.IFollowFriendsBiz;
import com.zf.weisport.presenter.biz.impl.FollowFriendsBizImpl;
import com.zf.weisport.ui.callback.IFollowCallback;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 14:57
 * @email Xs.lin@foxmail.com
 */
public class FollowViewModel extends BaseViewModel<IFollowCallback,MyFollowListModel> implements IFollowFriendsView {

    private IFollowFriendsBiz biz;
    public FollowViewModel(IFollowCallback iFollowCallback) {
        super(iFollowCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new FollowFriendsBizImpl(this,this);
    }

    /**
     * 获取已关注好友列表
     */
    public void getFollowFriendsList() {
        biz.getFollowFriends();
    }

    @Override
    public void onGetFollowFriendsCompleted(List<MyFollowListModel> followListModels) {
        getCallback().onGetFollowFriendSuccess(followListModels);
    }

    @Override
    public void onNetErrorNotifyUI() {
        getCallback().onNetError();
    }

    @Override
    public void onNetEmptyNotifyUI() {
        getCallback().onNetEmpty();
    }
}
