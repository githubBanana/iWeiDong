package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.TopicModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 17:35
 * @email Xs.lin@foxmail.com
 */
public interface ITopicView extends IBaseView {

    /**
     * 获取话题列表完成
     * @param topicModels
     */
    void onGetTopicCompleted(List<TopicModel> topicModels);

    /**
     * 获取更多话题列表完成
     * @param topicModels
     */
    void onGetMoreTopicCompleted(List<TopicModel> topicModels);

    /**
     * 访问网络错误
     */
    void onNetErrorNotifyUI();

    /**
     * 访问网络数据空回调
     */
    void onNetEmptyNotifyUI();

    /**
     * 设置请求类型
     * @param type
     */
    void setType(int type);

    /**
     * 获取请求类型
     * @return
     */
    int getType();

    /**
     * 设置请求类型对应的ID
     * @param objType
     */
    void setObjType(String objType);

    /**
     * 获取请求类型对应的ID
     * @return
     */
    String getObjType();

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
