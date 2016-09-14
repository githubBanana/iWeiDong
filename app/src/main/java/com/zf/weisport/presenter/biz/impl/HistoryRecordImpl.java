package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.presenter.IHistoryRecordView;
import com.zf.weisport.presenter.biz.IHistoryRecordBiz;

/**
 * @version V1.0 <访问历史转速数据业务层>
 * @author: Xs
 * @date: 2016-09-14 15:06
 * @email Xs.lin@foxmail.com
 */
public class HistoryRecordImpl extends BaseBiz<IHistoryRecordView> implements IHistoryRecordBiz {

    public HistoryRecordImpl(IHistoryRecordView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void loadInitData() {
        getView().setDeviceType("1");   //默认为 引力球
        getView().setType("1");         //默认为 周
    }

    @Override
    public void getHistoryRecordData() {
        showLoadingView();
        addSubscription(
            RequestHelper.getInstance().getStatistics(getView().getDeviceType(),
                    User.getUser().getId(),getView().getType()).
                doOnNext(statisticsModel -> {
                    if (statisticsModel.isSuccess()) {

                        if (getView().getType().equals("1"))
                            getView().setWeekModels(statisticsModel.getData());
                        if (getView().getType().equals("2"))
                            getView().setMonthModels(statisticsModel.getData());
                        if (getView().getType().equals("3"))
                            getView().setYearModes(statisticsModel.getData());

                        getView().onGetHistoryRecordDataCompleted(statisticsModel.getData());

                    } else
                        showToast(statisticsModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }
}
