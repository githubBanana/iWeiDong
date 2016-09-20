package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 14:08
 * @email Xs.lin@foxmail.com
 */
public interface ITopicLabelShowBiz extends IBaseBiz {

    /**
     * 获取话题标签列表
     */
    void getTopicLabelList();

    /**
     * 新建标签
     */
    void newLabel();
}
