package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.TopicCommentModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-19 11:43
 * @email Xs.lin@foxmail.com
 */
public interface ITopicCommentView extends IBaseView {

    /**
     * 获取话题评论列表完成
     * @param topicCommentModels
     */
    void onGetTopicCommentCompleted(List<TopicCommentModel> topicCommentModels);

    /**
     * 获取更多话题评论列表完成
     * @param topicCommentModels
     */
    void onGetMoreTopicCommentCompleted(List<TopicCommentModel> topicCommentModels);

    /**
     * 访问网络错误
     */
    void onNetErrorNotifyUI();

    /**
     * 访问网络数据空回调
     */
    void onNetEmptyNotifyUI();


    /**
     * 设置话题ID
     * @param topicId
     */
    void setTopicId(String topicId);

    /**
     * 获取话题ID
     * @return
     */
    String getTopicId();

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

    /**
     * 设置评论数目
     * @param commentCount
     */
    void setCommentCount(String commentCount);

    /**
     * 获取评论数目
     * @return
     */
    String getCommentCount();

    void setRealCommentCount(int realComment);

    int getRealCommentCount();

    /**
     * 设置评论输入内容
     * @param commentContent
     */
    void setCommentContent(String commentContent);

    /**
     * 获取评论输入内容
     * @return
     */
    String getCommentContent();

}
