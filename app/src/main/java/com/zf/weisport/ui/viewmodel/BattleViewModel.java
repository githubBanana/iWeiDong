package com.zf.weisport.ui.viewmodel;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.manager.db.bean.BleDevice;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.manager.util.UserUtil;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.model.UpGameModel;
import com.zf.weisport.presenter.IBattleView;
import com.zf.weisport.presenter.biz.IBattleBiz;
import com.zf.weisport.presenter.biz.impl.BattleBizImpl;
import com.zf.weisport.ui.callback.IBattleCallback;

/**
 * @version V1.0 <对战数据处理层>
 * @author: Xs
 * @date: 2016-09-22 16:47
 * @email Xs.lin@foxmail.com
 */
public class BattleViewModel extends BaseViewModel<IBattleCallback,MyRankModel> implements IBattleView {


    public String deviceAddress;
    public String deviceName;
    public String deviceId ;
    public String deviceType ;

    private IBattleBiz biz;
    public BattleViewModel(IBattleCallback iBattleCallback) {
        super(iBattleCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new BattleBizImpl(this,this);
    }

    /**
     * 获取我的排名
     */
    public void getMyRank() {
        if (UserUtil.isLogin(UIUtil.getContext()))
            biz.getMyRank();
    }

    /**
     * 绑定设备
     */
    public void bindDevice(String address,String deviceName) {
        setDeviceAddress(address);
        setDeviceName(deviceName);

        //将型如11:22:33:44:55:66 转为112233445566
        String a[] = address.split(":");
        StringBuffer sb = new StringBuffer();
        for (String s:a) {sb.append(s);}

        //未登陆 无需绑定
        if (!UserUtil.isLogin(UIUtil.getContext())) {
            //本地保存macAddress 无用户登陆
            setDeviceId("0");//无用户登录
            setDeviceType(BleDevice.DEVICE_TYPE_BOLL);//默认腕力球
            AppDatabaseCache.getCache(UIUtil.getContext()).saveBleDevice(getDeviceType(),
                    getDeviceId(),address,deviceName);
            return;
        }

        biz.bindDevice(sb.toString(),deviceName);

    }

    /**
     * 上传 下位机历史记录
     */
    public void upGameArr() {

        if (!UserUtil.isLogin(UIUtil.getContext()))
            return;
        if (TextUtils.isEmpty(getDeviceId()) || "0".equals(getDeviceId())) {//说明上次网络绑定设备时出错，需重新绑定一次
            bindDevice(getDeviceAddress(),getDeviceName());
            return;
        }

        biz.upGameArr();
    }

    /**
     * 游戏记录上传
     * @param Calorie
     * @param Start_Time
     * @param Long_Time
     * @param Speed
     */
    public void upGame(String Calorie,String Start_Time,String Long_Time,String Speed){
        String userId = "";
        if (UserUtil.isLogin(UIUtil.getContext())) {
            if (TextUtils.isEmpty(getDeviceId()) || "0".equals(getDeviceId())) { //说明上次网络绑定设备时出错，需重新绑定一次
                if (!TextUtils.isEmpty(getDeviceAddress()))
                    bindDevice(getDeviceAddress(),getDeviceName());
                return;
            }
            userId = User.getUser().getId();
        } else {
            userId = "0";
        }

        biz.upGame(userId,Calorie, Start_Time, Long_Time, Speed);
    }
    @Override
    public void onGetMyRankCompleted(MyRankModel myRankModel) {
        //update user and sql
        String levelString = myRankModel.getLevelId();
        User.getUser().setLevel(levelString);
        AppDatabaseCache.getCache(UIUtil.getContext()).updateUser_Level(levelString);

        sortDangrad(levelString);
    }

    @Override
    public void onUpGameArrCompleted() {
        getCallback().onUpGameArrSuccess();
    }

    @Override
    public void onUpGameCompleted(UpGameModel upGameModel) {
        getCallback().onUpGameSuccess(upGameModel);
    }

    @Override
    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    @Override
    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    @Override
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String getDeviceName() {
        return this.deviceName;
    }

    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String getDeviceId() {
        return this.deviceId;
    }

    @Override
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String getDeviceType() {
        return this.deviceType;
    }

    /**
     * 将对应的排名名称+排名级别取出
     * @param levelString
     */
    private void sortDangrad(String levelString) {
        final int level = Integer.valueOf(levelString);
        int speedLevel = 0;
        String levelSpecial = "";
        switch (level) {
            case 37:
                levelSpecial = "最强王者";
                speedLevel = 14000;
                break;
            case 36:
                levelSpecial = "至尊钻石";
                speedLevel = 12000;
                break;
            case 35:
                levelSpecial = "荣耀黄金";
                speedLevel = 9000;
                break;
            case 34:
                levelSpecial = "不屈白银";
                speedLevel = 7000;
                break;
            case 45:
                levelSpecial = "英勇黄铜";
                speedLevel = 5000;
                break;
            default:
                levelSpecial = "未知等级";
                speedLevel = 0;
                break;
        }
        getCallback().onGetMyRankSuccess(levelSpecial,speedLevel);
    }
}
