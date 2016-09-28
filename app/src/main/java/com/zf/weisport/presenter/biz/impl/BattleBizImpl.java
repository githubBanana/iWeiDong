package com.zf.weisport.presenter.biz.impl;

import android.util.Log;

import com.diy.blelib.bag.HistoryBag;
import com.diy.blelib.bag.HistoryBagGroup;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.UpGameBean;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.DeviceConnectModel;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.presenter.IBattleView;
import com.zf.weisport.presenter.biz.IBattleBiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                            getView().setDeviceType(model.getDevice_Type());
                            getView().setDeviceId(model.getID());
                            AppDatabaseCache.getCache(UIUtil.getContext()).saveBleDevice(model.Device_Type,
                                    model.ID,address,deviceName);
                        }
                    } else {
                        showToast(deviceConnectModel.getErrMsg());
                    }
                }).subscribe(getSubscriber())
        );
    }

    @Override
    public void upGameArr() {
        List<HashMap<String,Object>> upLists = new ArrayList<>();
        List<HistoryBag> bags = HistoryBagGroup.getBagGroup().getBagList();
        //history bag turn to UpGameBean :HistoryBag 为下位机协议封装，UpGameBean为服务器端协议封装
        if (bags.size() == 0)
            return;
        for (int i = 0; i < bags.size(); i++) {
            HistoryBag bag = bags.get(i);
            UpGameBean bean = new UpGameBean();
            bean.setUser_ID(User.getUser().getId());
            bean.setDevice_ID(getView().getDeviceId());
            bean.setCalorie("200");
            bean.setDevice_Type("1");
            bean.setStart_Time(String.valueOf(bag.getStartTime()));
            bean.setLong_Time(String.valueOf(bag.getDuration()));
            bean.setSpeed(String.valueOf(bag.getMaxSpeed()));
            upLists.add(bean.toMap());
        }

        for (int i = 0; i < upLists.size(); i++) {
            Log.e("info","upGameArr:history bag turn to UpGameBean : "+upLists.get(i).toString());
        }

        addSubscription(
                RequestHelper.getInstance().upGameArr(upLists)
                .doOnNext(baseModel -> {
                    if (baseModel.isSuccess())
                        getView().onUpGameArrCompleted();
                    else
                        showToast(baseModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }

    @Override
    public void upGame(String userId,String Calorie, String Start_Time, String Long_Time, String Speed) {
        addSubscription(
                RequestHelper.getInstance().UpGame(userId,getView().getDeviceId(),Calorie,getView().getDeviceType()
                ,Start_Time,Long_Time,Speed)
                .doOnNext(upGameModel -> {
                    if (upGameModel.isSuccess())
                        getView().onUpGameCompleted(upGameModel.getData().get(0));
                    else
                        showToast(upGameModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }
}
