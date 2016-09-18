package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 15:13
 * @email Xs.lin@foxmail.com
 */
public interface IFollowFriendsBiz extends IBaseBiz {

    /**
     * 获取已关注好友列表
     */
    void getFollowFriends();
}
