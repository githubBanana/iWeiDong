package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.TopicCommentModel;
import com.zf.weisport.presenter.ITopicCommentView;
import com.zf.weisport.presenter.biz.ITopicCommentBiz;
import com.zf.weisport.presenter.biz.impl.TopicCommentBizImpl;
import com.zf.weisport.ui.callback.ITopicCommentCallback;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-19 11:39
 * @email Xs.lin@foxmail.com
 */
public class TopicCommentViewModel extends BaseViewModel<ITopicCommentCallback,TopicCommentModel> implements ITopicCommentView {

    public String topicId;
    public int pageIndex;

    public String commentCount;
    public int realCommentCount;
    public String commentContent;

    private ITopicCommentBiz biz;

    public TopicCommentViewModel(ITopicCommentCallback iTopicCommentCallback) {
        super(iTopicCommentCallback);
        biz = getBiz();
        biz.loadInitData();
    }

    @Override
    protected BaseBiz createBiz() {
        return new TopicCommentBizImpl(this,this);
    }

    /**
     * 获取话题评论列表
     */
    public void getTopicCommentList() {
        biz.getTopicCommentList();
    }

    /**
     * 发布评论
     */
    public void releaseComment() {
        biz.releaseComment();
    }

    public void getMoreTopicCommentList() {
        biz.getMoreTopicCommentList();
    }

    @Override
    public void onGetTopicCommentCompleted(List<TopicCommentModel> topicCommentModels) {
        getCallback().onGetCommentListSuccess(topicCommentModels);
    }

    @Override
    public void onGetMoreTopicCommentCompleted(List<TopicCommentModel> topicCommentModels) {
        getCallback().onGetMoreCommentListSuccess(topicCommentModels);
    }

    @Override
    public void onNetErrorNotifyUI() {
        getCallback().onNetError();
    }

    @Override
    public void onNetEmptyNotifyUI() {
        getCallback().onNetEmpty();
    }

    @Override
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @Override
    public String getTopicId() {
        return this.topicId;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override
    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String getCommentCount() {
        return this.commentCount;
    }

    @Override
    public void setRealCommentCount(int realComment) {
        this.realCommentCount = realComment;
    }

    @Override
    public int getRealCommentCount() {
        return this.realCommentCount;
    }

    @Override
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Override
    public String getCommentContent() {
        return this.commentContent;
    }
}
