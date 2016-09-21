package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 15:59
 * @email Xs.lin@foxmail.com
 */
public interface IRankingBiz extends IBaseBiz {

    /**
     * 获取我的排名
     */
    void getMyRank();

    /**
     * 获取全国排名列表
     */
    void getRankList();
}
