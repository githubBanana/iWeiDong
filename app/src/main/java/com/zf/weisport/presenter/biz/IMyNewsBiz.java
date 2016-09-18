package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;
import com.zf.weisport.model.MyMessageModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 09:37
 * @email Xs.lin@foxmail.com
 */
public interface IMyNewsBiz extends IBaseBiz{

    /**
     * 获取我的消息
     */
    void getMyMessage();

    /**
     * 获取更多我的消息
     */
    void getMoreMyMessage();

    /**
     * 读信息
     * @param myMessageModel
     * @param position
     */
    void readMessage(int position,MyMessageModel myMessageModel);
}
