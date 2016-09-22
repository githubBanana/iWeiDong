package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.presenter.IBattleView;
import com.zf.weisport.presenter.biz.IBattleBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-22 16:55
 * @email Xs.lin@foxmail.com
 */
public class BattleBizImpl extends BaseBiz<IBattleView> implements IBattleBiz {

    public BattleBizImpl(IBattleView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getMyRank() {
        addSubscription(
                RequestHelper.getInstance().requestGetMyRank(User.getUser().getId())
                .doOnNext(myRankModel -> {
                    if (myRankModel.isSuccess())
                        getView().onGetMyRankCompleted(myRankModel.getData().get(0));
                    else
                        showToast(myRankModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }
}
