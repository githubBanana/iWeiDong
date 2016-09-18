package com.zf.weisport.ui.callback;

import com.zf.weisport.model.MyFollowListModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 14:58
 * @email Xs.lin@foxmail.com
 */
public interface IFollowCallback extends IBaseCallback {

    /**
     * 获取关注好友列表成功
     * @param followListModels
     */
    void onGetFollowFriendSuccess(List<MyFollowListModel> followListModels);
}
