package com.zf.weisport.ui.callback;

import com.zf.weisport.model.TopicCommentModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-19 11:41
 * @email Xs.lin@foxmail.com
 */
public interface ITopicCommentCallback extends IBaseCallback {

    /**
     * 获取话题评论列表成功
     * @param topicCommentModels
     */
    void onGetCommentListSuccess(List<TopicCommentModel> topicCommentModels);

    /**
     * 获取更多话题评论列表成功
     * @param topicCommentModels
     */
    void onGetMoreCommentListSuccess(List<TopicCommentModel> topicCommentModels);
}
