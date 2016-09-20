package com.zf.weisport.presenter.biz.impl;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.presenter.ITopicLabelShowView;
import com.zf.weisport.presenter.biz.ITopicLabelShowBiz;

/**
 * @version V1.0 <获取话题标签列表业务层>
 * @author: Xs
 * @date: 2016-09-20 14:09
 * @email Xs.lin@foxmail.com
 */
public class TopicLabelShowBizImpl extends BaseBiz<ITopicLabelShowView> implements ITopicLabelShowBiz {

    public TopicLabelShowBizImpl(ITopicLabelShowView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getTopicLabelList() {
        addSubscription(
                RequestHelper.getInstance().getlabel(User.getUser().getId()).
                        doOnNext(getLabelModel -> {
                            if (getLabelModel.isSuccess())
                                getView().onGetTopicLabelListCompleted(getLabelModel.getData());
                        }).subscribe(getSubscriber())
        );
    }

    @Override
    public void newLabel() {
        String labelContent = TextUtils.isEmpty(getView().getLabelContent()) ? "" : getView().getLabelContent();
        if (TextUtils.isEmpty(labelContent)) {
            showToast(R.string.topic_lable_add3);
            return;
        }

        addSubscription(
                RequestHelper.getInstance().newLabel(User.getUser().getId(),labelContent).
                        doOnNext(addLabelModel -> {
                            if (addLabelModel.isSuccess()) {
                                getView().onNewLabelCompleted(addLabelModel.getData().get(0));
                            }
                        }).subscribe(getSubscriber())
        );
    }
}
