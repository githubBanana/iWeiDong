package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.MyMessageModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 09:38
 * @email Xs.lin@foxmail.com
 */
public interface IMyNewsView extends IBaseView {

    /**
     * 获取我的消息完成回调
     */
    void onGetMyMessageCompleted(List<MyMessageModel> myMessageModels);

    /**
     * 获取更多我的消息完成回调
     * @param myMessageModels
     */
    void onGetMoreMyMessageCompleted(List<MyMessageModel> myMessageModels);

    /**
     * 读信息
     */
    void onReadMessageCompleted(int position,MyMessageModel myMessageModel);

    /**
     * 访问网络错误
     */
    void onNetErrorNotifyUI();

    /**
     * 访问网络数据空回调
     */
    void onNetEmptyNotifyUI();

    /**
     * 结束刷新View
     */
    void onFinishRefreshView();

    /**
     * 设置页码
     * @param pageIndex
     */
    void setPageIndex(int pageIndex);

    /**
     * 获取页码
     * @return
     */
    int getPageIndex();
}
