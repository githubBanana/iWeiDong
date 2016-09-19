package com.zf.weisport.presenter.biz.impl;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.model.TopicCommentModel;
import com.zf.weisport.presenter.ITopicCommentView;
import com.zf.weisport.presenter.biz.ITopicCommentBiz;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-19 11:50
 * @email Xs.lin@foxmail.com
 */
public class TopicCommentBizImpl extends BaseBiz<ITopicCommentView> implements ITopicCommentBiz {

    private int _pageSize = 1000;

    public TopicCommentBizImpl(ITopicCommentView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void loadInitData() {
        getView().setPageIndex(1);
        getView().setRealCommentCount(0);
        getView().setCommentCount("评论(0)");
    }

    @Override
    public void getTopicCommentList() {
        addSubscription(
            RequestHelper.getInstance().requestGetCommentList(getView().getTopicId(),getView().getPageIndex(),_pageSize).
                subscribe(new Subscriber<TopicCommentModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showToast(R.string.net_error_toast);
                        getView().onNetErrorNotifyUI();
                    }

                    @Override
                    public void onNext(TopicCommentModel topicCommentModel) {
                        if (topicCommentModel.isSuccess()) {
                            if (topicCommentModel.isEmptyData())
                                getView().onNetEmptyNotifyUI();
                            else {
                                getView().setRealCommentCount(topicCommentModel.getDataCount());
                                getView().setCommentCount("评论("+
                                        topicCommentModel.getDataCount()+")");
                                getView().notifyUIChange();
                                getView().onGetTopicCommentCompleted(topicCommentModel.getData());
                            }
                        } else {
                            showToast(topicCommentModel.getErrMsg());
                        }
                    }
                })
    );
    }

    @Override
    public void releaseComment() {
        if (TextUtils.isEmpty(getView().getCommentContent())) {
            showToast(R.string.text_input_comment);
            return;
        }
        showLoadingView();
        addSubscription(
            RequestHelper.getInstance().requestAddComment(
                User.getUser().getId(),getView().getTopicId(),getView().getCommentContent()).
                doOnNext(baseModel -> {
                    if (baseModel.isSuccess()) {
                        getTopicCommentList();
                        showToast(R.string.text_release_success);
                        getView().setCommentContent("");
                        getView().notifyUIChange();
                    } else {
                        showToast(baseModel.getErrMsg());
                    }
                }).subscribe(getSubscriber())

    );
    }

    @Override
    public void getMoreTopicCommentList() {
        addPageIndex();
        addSubscription(
                RequestHelper.getInstance().requestGetCommentList(getView().getTopicId(),getView().getPageIndex(),_pageSize).
                        subscribe(new Subscriber<TopicCommentModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                showToast(R.string.net_error_toast);
                                reducePageIndex();
                                getView().onGetMoreTopicCommentCompleted(null);
                            }

                            @Override
                            public void onNext(TopicCommentModel topicCommentModel) {
                                if (topicCommentModel.isSuccess()) {
                                    if (topicCommentModel.isEmptyData()) {
                                        getView().onGetMoreTopicCommentCompleted(null);
                                        reducePageIndex();
                                    }
                                    else
                                        getView().onGetMoreTopicCommentCompleted(topicCommentModel.getData());
                                } else {
                                    showToast(topicCommentModel.getErrMsg());
                                    getView().onGetMoreTopicCommentCompleted(null);
                                    reducePageIndex();
                                }
                            }
                        })
        );
    }

    /**
     * 页码+1
     */
    private void addPageIndex() {
        int currentPageIndex = getView().getPageIndex();
        getView().setPageIndex(++currentPageIndex);
    }

    /**
     * 页码-1
     */
    private void reducePageIndex() {
        int currentPageIndex = getView().getPageIndex();
        getView().setPageIndex(--currentPageIndex);
    }
}
