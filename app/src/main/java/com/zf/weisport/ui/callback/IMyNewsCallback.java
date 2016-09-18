package com.zf.weisport.ui.callback;

import com.zf.weisport.model.MyMessageModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 09:19
 * @email Xs.lin@foxmail.com
 */
public interface IMyNewsCallback extends IBaseCallback {

    /**
     * 获取我的消息成功回调
     * @param myMessageModels
     */
    void onGetMyMessageSuccess(List<MyMessageModel> myMessageModels);

    /**
     * 获取更多我的消息成功回调
     * @param myMessageModels
     */
    void onGetMoreMyMessageSuccess(List<MyMessageModel> myMessageModels);

    /**
     * 读信息
     */
    void onReadMessageSuccess(int position,MyMessageModel myMessageModel);
}
