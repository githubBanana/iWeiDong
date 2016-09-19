package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.model.TopicModel;
import com.zf.weisport.presenter.ITopicView;
import com.zf.weisport.presenter.biz.ITopicBiz;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 17:34
 * @email Xs.lin@foxmail.com
 */
public class TopicBizImpl extends BaseBiz<ITopicView> implements ITopicBiz {

    private int _pageSize = 100;
    public TopicBizImpl(ITopicView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getTopic() {
        getView().setPageIndex(1);
        addSubscription(
                RequestHelper.getInstance().requestGetTopic(getView().getType(),getView().getObjType(),
                        User.getUser().getId(),getView().getPageIndex(),_pageSize)
                .subscribe(new Subscriber<TopicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().onNetErrorNotifyUI();
                        showToast(R.string.net_error_toast);
                    }

                    @Override
                    public void onNext(TopicModel topicModel) {
                        if (topicModel.isSuccess()) {
                            if (topicModel.isEmptyData())
                                getView().onNetEmptyNotifyUI();
                            else
                                getView().onGetTopicCompleted(topicModel.getData());
                        } else {
                            showToast(topicModel.getErrMsg());
                        }
                    }
                })
        );
    }

    @Override
    public void getMoreTopic() {
        addPageIndex();
        addSubscription(
            RequestHelper.getInstance().requestGetTopic(getView().getType(),getView().getObjType(),
                User.getUser().getId(),getView().getPageIndex(),_pageSize).
                subscribe(new Subscriber<TopicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showToast(R.string.net_error_toast);
                        reducePageIndex();
                        getView().onGetMoreTopicCompleted(null);
                    }

                    @Override
                    public void onNext(TopicModel topicModel) {
                        if (topicModel.isSuccess()) {
                            if (topicModel.isEmptyData()) {
                                reducePageIndex();
                                getView().onGetMoreTopicCompleted(null);
                            } else
                                getView().onGetMoreTopicCompleted(topicModel.getData());
                        } else {
                            showToast(topicModel.getErrMsg());
                            reducePageIndex();
                            getView().onGetMoreTopicCompleted(null);
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
