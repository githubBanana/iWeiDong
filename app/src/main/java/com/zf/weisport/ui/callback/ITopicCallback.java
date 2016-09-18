package com.zf.weisport.ui.callback;

import com.zf.weisport.model.TopicModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 17:26
 * @email Xs.lin@foxmail.com
 */
public interface ITopicCallback extends IBaseCallback {

    /**
     * 获取话题列表成功
     * @param topicModels
     */
    void onGetTopicSuccess(List<TopicModel> topicModels);
}
