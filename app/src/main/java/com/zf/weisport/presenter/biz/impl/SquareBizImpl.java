package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.model.LabelTopicModel;
import com.zf.weisport.presenter.ISquareView;
import com.zf.weisport.presenter.biz.ISquareBiz;

import rx.Subscriber;

/**
 * @version V1.0 <广场界面业务层>
 * @author: Xs
 * @date: 2016-09-10 09:54
 * @email Xs.lin@foxmail.com
 */
public class SquareBizImpl extends BaseBiz<ISquareView> implements ISquareBiz {

    public SquareBizImpl(ISquareView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getLabelTopic() {
        addSubscription(
            RequestHelper.getInstance().getLabelTopic().
                subscribe(new Subscriber<LabelTopicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onNetErrorNotifyUI();
                    }

                    @Override
                    public void onNext(LabelTopicModel labelTopicModel) {
                        if (labelTopicModel.isSuccess()) {
                            if (labelTopicModel.isEmptyData())
                                getView().onNetEmptyNotifyUI();
                            else
                                getView().onGetLabelTopicCompleted(labelTopicModel.getData());
                        } else {
                            showToast(labelTopicModel.getErrMsg());
                        }
                    }
                })
        );
    }

    @Override
    public void getTop() {
        addSubscription(
            RequestHelper.getInstance().getTop().
                doOnNext(getTopModel -> {
                    if (getTopModel.isSuccess() && !getTopModel.isEmptyData()) {
                        getView().onGetTopCompleted(getTopModel.getData());
                    }
                }).subscribe(getSubscriber())
        );
    }
}
