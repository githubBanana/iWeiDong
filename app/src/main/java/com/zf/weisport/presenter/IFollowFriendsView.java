package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.MyFollowListModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 15:14
 * @email Xs.lin@foxmail.com
 */
public interface IFollowFriendsView extends IBaseView {

    /**
     * 获取已关注好友列表完成
     * @param followListModels
     */
    void onGetFollowFriendsCompleted(List<MyFollowListModel> followListModels);

    /**
     * 访问网络错误
     */
    void onNetErrorNotifyUI();

    /**
     * 访问网络数据空回调
     */
    void onNetEmptyNotifyUI();

}
