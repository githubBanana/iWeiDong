package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.model.GetRankModel;
import com.zf.weisport.presenter.IRankingView;
import com.zf.weisport.presenter.biz.IRankingBiz;

import rx.Subscriber;

/**
 * @version V1.0 <排名业务层>
 * @author: Xs
 * @date: 2016-09-21 16:00
 * @email Xs.lin@foxmail.com
 */
public class RankingBizImpl extends BaseBiz<IRankingView> implements IRankingBiz {

    public RankingBizImpl(IRankingView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getMyRank() {

        addSubscription(
                RequestHelper.getInstance().requestGetMyRank(User.getUser().getId()).
                        doOnNext(myRankModel -> {
                            if (myRankModel.isSuccess())
                                getView().onGetMyRankCompleted(myRankModel.getData().get(0));
                        }).subscribe(getSubscriber())
        );
    }

    @Override
    public void getRankList() {
        String pageIndex = "1";
        addSubscription(
                RequestHelper.getInstance().requestGetRank(pageIndex).
                        subscribe(new Subscriber<GetRankModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                getView().onNetErrorNotifyUI();
                            }

                            @Override
                            public void onNext(GetRankModel getRankModel) {
                                if (getRankModel.isSuccess()) {
                                    if (getRankModel.isEmptyData())
                                        getView().onNetEmptyNotifyUI();
                                    else
                                        getView().onGetRankListCompleted(getRankModel.getData());
                                } else {
                                    getView().onNetErrorNotifyUI();
                                    showToast(getRankModel.getErrMsg());
                                }
                            }
                        })
        );
    }
}
