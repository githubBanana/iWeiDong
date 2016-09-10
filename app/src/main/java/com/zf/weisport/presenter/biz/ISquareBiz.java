package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 09:52
 * @email Xs.lin@foxmail.com
 */
public interface ISquareBiz extends IBaseBiz{

    /**
     * 获取话题标签
     */
    void getLabelTopic();

    /**
     * 获取轮播图
     */
    void getTop();
}
