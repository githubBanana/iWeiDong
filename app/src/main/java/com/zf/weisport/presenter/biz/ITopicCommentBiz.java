package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-19 11:49
 * @email Xs.lin@foxmail.com
 */
public interface ITopicCommentBiz extends IBaseBiz{

    /**
     * 获取话题评论列表
     */
    void getTopicCommentList();

    /**
     * 发布评论
     */
    void releaseComment();

    /**
     * 获取更多话题评论列表
     */
    void getMoreTopicCommentList();
}
