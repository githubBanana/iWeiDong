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
public class TopicImpl extends BaseBiz<ITopicView> implements ITopicBiz {

    public TopicImpl(ITopicView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getTopic() {
        int pageIndex = 1;
        int pageSize = 100;
        addSubscription(
                RequestHelper.getInstance().requestGetTopic(getView().getType(),getView().getObjType(), User.getUser().getId(),pageIndex,pageSize)
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
}
