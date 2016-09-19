package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 17:34
 * @email Xs.lin@foxmail.com
 */
public interface ITopicBiz extends IBaseBiz {

    /**
     * 获取话题
     */
    void getTopic();

    /**
     * 获取更多话题
     */
    void getMoreTopic();
}
