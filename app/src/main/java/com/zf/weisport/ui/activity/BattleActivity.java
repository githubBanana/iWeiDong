package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diy.blelib.bag.ActiveSend;
import com.diy.blelib.bag.BagCommand;
import com.diy.blelib.bag.BagHandler;
import com.diy.blelib.bag.HistoryBagGroup;
import com.diy.blelib.bag.RealTimeBag;
import com.diy.blelib.ble.sport.SportService;
import com.diy.blelib.profile.BleProfileService;
import com.diy.blelib.profile.bleutils.BleConstant;
import com.diy.blelib.profile.bleutils.BleUUID;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.other.GameTimeCount;
import com.zf.weisport.manager.util.GlideUtil;
import com.zf.weisport.manager.widget.pointer.DrawDangradView;
import com.zf.weisport.manager.widget.pointer.SpeedPointer;
import com.zf.weisport.model.UpGameModel;
import com.zf.weisport.ui.activity.ble.BleProfileServiceReadyActivity;
import com.zf.weisport.ui.callback.IBattleCallback;
import com.zf.weisport.ui.viewmodel.BattleViewModel;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @version V1.0 <battle界面>
 * @author: Xs
 * @date: 2016-09-22 14:34
 * @email Xs.lin@foxmail.com
 */
public class BattleActivity extends BleProfileServiceReadyActivity<SportService.RSCBinder>
        implements SpeedPointer.OnListenSpeed,IBattleCallback,GameTimeCount.TimeCountCallBack{

    @Bind(R.id.battle_user_name_Id) TextView mTvUserName;
    @Bind(R.id.tv_my_level)         TextView mTvLevel;
    @Bind(R.id.battle_headId)       ImageView mIvHead;
    @Bind(R.id.tv_timeCountId)      TextView mTvTimeCount;
    @Bind(R.id.pointer)             SpeedPointer mPointer;
    @Bind(R.id.easy_speedtextId)    TextView mTvSpeedValue;
    @Bind(R.id.easy_maxvalueId)     TextView mTvSpeedMaxValue;
    @Bind(R.id.dangradview)         DrawDangradView mDangrad;
    @Bind(R.id.tv_battery_Id)       TextView mTvBattery;
    @Bind(R.id.ble_conn)            TextView mTvConn;

    private BattleViewModel _viewModel;
    private GameTimeCount   mGameTimeCount;
    private int             _rememberCurrSpeedLevel = 0;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,BattleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected Class<? extends BleProfileService> getServiceClass() {
        return SportService.class;
    }

    @Override
    protected UUID[] getFilterUUID() {
        return new UUID[]{BleUUID.TP_SERVICE_UUID,BleUUID.BATTERY_SERVICE};
    }

    @Override
    protected boolean isAutoScanSetting() {
        return true;
    }

    @Override
    protected String theLastConnDeviceAddress() {
        Log.e("info", "theLastConnDeviceAddress: "+AppDatabaseCache.getCache(this).getLastConnectedAddress() );
        return AppDatabaseCache.getCache(this).getLastConnectedAddress();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_battle);
        ButterKnife.bind(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,new IntentFilter(SportService.BROADCAST_SPORT_MEASUREMENT));
        _viewModel = new BattleViewModel(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(_receiver);
        //清除 全局单例
        HistoryBagGroup.getBagGroup().clear();
        RealTimeBag.getBag().clear();
    }

    private void initView() {
        mPointer.setOnlistenSpeed(this);
        mGameTimeCount = new GameTimeCount();
        mGameTimeCount.setTimeCountListen(this);

        if (!TextUtils.isEmpty(User.getUser().getHeadUrl()))
            GlideUtil.showRectHead(User.getUser().getHeadUrl(),mIvHead);
        if (!TextUtils.isEmpty(User.getUser().getName()))
            mTvUserName.setText(User.getUser().getName());
        _viewModel.getMyRank();
    }


    @Override
    protected void setUIConnectStatus(int status) {
        switch (status) {
            case BleConstant.STATE_CONNECTED:
                dismissLoadingView();
                mTvConn.setText(R.string.connected_text);
                showToastCustomView(R.string.connected_device_text);
                _viewModel.bindDevice(getDeviceAddress(),getDeviceName());//连接成功，网络绑定设备
                break;
            case BleConstant.STATE_DISCONNECTED:
                mTvConn.setText(R.string.dis_connect_text);
                showToastCustomView(R.string.disconnect_device_text);
                /*异常断开 计时器归零*/
                mGameTimeCount.stop(false);
                break;
        }
    }

    /**
     * 接收蓝牙数据广播
     */
    private BroadcastReceiver _receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (SportService.BROADCAST_SPORT_MEASUREMENT.equals(action)) {
                final float value = intent.getFloatExtra(SportService.EXTRA_SPORT,0.0f);
                runOnUiThread(() -> {
                    byte config = intent.getByteExtra(SportService.EXTRA_SPORT,(byte)0x00);
                    switch (config) {
                        case BagCommand.BAG_RECEIVE_REALTIME:
                            updateUIForRealTimeBag();
                            break;
                        case BagCommand.BAG_RECEIVE_HISTORY_END:
                            // nothing to do
                            break;
                    }
                });
            } else if (SportService.BROADCAST_UPGAMEARR.equals(action)) {//上传历史记录
                _viewModel.upGameArr();
            }
        }
    };

    /**
     * ui实时更新
     */
    private void updateUIForRealTimeBag() {
        if (RealTimeBag.getBag().getRealTimeSpeed() >= BagHandler.START_COUNT_VALUE)
            mGameTimeCount.start();
        else
            mGameTimeCount.stop(true);

        runOnUiThread(() -> {
            mPointer.setSpeed(RealTimeBag.getBag().getRealTimeSpeed());
            mTvSpeedValue.setText(String.valueOf(RealTimeBag.getBag().getRealTimeSpeed() + "转"));
            mTvSpeedMaxValue.setText(String.valueOf(RealTimeBag.getBag().getMaxSpeed() + "转"));
            mTvBattery.setText(getString(R.string.battery) + " " + RealTimeBag.getBag().getBattery() + "%");
        });
    }

    @Override
    public void speedCallBack(int speed) {

    }

    @OnClick({R.id.leave_easybattle_id,R.id.rank_record_tv,R.id.speed_record_id})
    public void battleOnclick(View view) {
        switch (view.getId()) {
            case R.id.leave_easybattle_id:
                showLeaveView();
                break;
            case R.id.rank_record_tv:
                RankingActivity.start(this);
                break;
            case R.id.speed_record_id:
                List<Integer> list = RealTimeBag.getBag().getSpeedList();
                if (list.size() > 1)
                    LastSpeedActivity.start(this);
                else
                    showToastCustomView(R.string.no_realtime_data);
                break;
        }
    }

    private void showLeaveView() {
        new AlertDialog.Builder(this).setTitle(R.string.text_tips)
                .setMessage(R.string.leave_device_text)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    finish();
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }).show();
    }

    @Override
    public void onBackPressed() {
        showLeaveView();
    }

    @Override
    public void onGetMyRankSuccess(String levelString, int speedLevel) {
        mTvLevel.setText(levelString);
        mDangrad.setVictorValue(speedLevel);
        _rememberCurrSpeedLevel = speedLevel;
    }

    @Override
    public void onUpGameArrSuccess() {
        if (getService() != null)
            getService().sendUserCommand(ActiveSend.toClearHistory());
    }

    @Override
    public void onUpGameSuccess(UpGameModel upGameModel) {
        if (RealTimeBag.getBag().getMaxSpeed() > _rememberCurrSpeedLevel)
            _viewModel.getMyRank();
        BattleResultActivity.start(this,upGameModel.getPercent(),upGameModel.getGameId());
    }

    @Override
    public void OnGameRun(String run, int second) {
        mTvTimeCount.post(() -> mTvTimeCount.setText(run));
    }

    @Override
    public void OnGameOver(boolean IsNormal, String end_string, String end_second) {
        Log.e("info", "OnGameOver: "+IsNormal+" "+end_string+" "+end_second );
        //设置结束时间、时长
        RealTimeBag realTimeBag = RealTimeBag.getBag();
        realTimeBag.setEndTime(end_string);
        realTimeBag.setEndTimeStamp(end_second);
        if (IsNormal) {
//            realTimeBag.setMaxSpeed(12000); just test
            Log.e("info", "游戏结束，开始上传实时数据...实时包:" + RealTimeBag.getBag().toString());
            _viewModel.upGame("200",realTimeBag.getStartTimeStamp(),realTimeBag.getEndTimeStamp(),String.valueOf(realTimeBag.getMaxSpeed()));
        } else {//中途断链
            OnGameRun("00:00",0);//UI清零
            runOnUiThread(() -> {
                mPointer.setSpeed(0);
                mTvSpeedValue.setText("0转");
            });
        }
    }

    @Override
    public void OnGameStart(String startTime, String startTimeStamp) {
        Log.e("info", "OnGameStart: "+startTime+"  "+startTimeStamp );
        //设置开始时间
        RealTimeBag.getBag().clearSpeedList();
        RealTimeBag.getBag().setStartTime(startTime);//本地时间格式
        RealTimeBag.getBag().setStartTimeStamp(startTimeStamp);//时间戳格式
    }
}
