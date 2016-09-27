package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.DeviceConnectModel;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.presenter.IBattleView;
import com.zf.weisport.presenter.biz.IBattleBiz;

import rx.Subscriber;

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
                .subscribe(new Subscriber<MyRankModel>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showToast(R.string.net_error_toast);
                    }
                    @Override
                    public void onNext(MyRankModel myRankModel) {
                        if (myRankModel.isSuccess())
                            getView().onGetMyRankCompleted(myRankModel.getData().get(0));
                        else
                            showToast(myRankModel.getErrMsg());
                    }
                })
        );
    }

    @Override
    public void bindDevice(String address,String deviceName) {

        addSubscription(
                RequestHelper.deviceConnect(User.getUser().getId(),address)
                .doOnNext(deviceConnectModel -> {
                    if (deviceConnectModel.isSuccess()) {
                        if (!deviceConnectModel.isEmptyData()) {
                            // 本地保存macAddress 有用户登录
                            DeviceConnectModel model = deviceConnectModel.getData().get(0);
                            AppDatabaseCache.getCache(UIUtil.getContext()).saveBleDevice(model.Device_Type,
                                    model.ID,address,deviceName);
                        }
                    } else {
                        showToast(deviceConnectModel.getErrMsg());
                    }
                }).subscribe(getSubscriber())
        );
    }
}
