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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diy.blelib.ble.hts.HtsService;
import com.diy.blelib.profile.BleProfileService;
import com.diy.blelib.profile.bleutils.BleConstant;
import com.diy.blelib.profile.bleutils.BleUUID;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.util.GlideUtil;
import com.zf.weisport.manager.widget.pointer.DrawDangradView;
import com.zf.weisport.manager.widget.pointer.SpeedPointer;
import com.zf.weisport.ui.activity.ble.BleProfileServiceReadyActivity;
import com.zf.weisport.ui.callback.IBattleCallback;
import com.zf.weisport.ui.viewmodel.BattleViewModel;

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
public class BattleActivity extends BleProfileServiceReadyActivity<HtsService.RSCBinder>
        implements SpeedPointer.OnListenSpeed,IBattleCallback{

    @Bind(R.id.battle_user_name_Id) TextView mTvUserName;
    @Bind(R.id.tv_my_level)         TextView mTvLevel;
    @Bind(R.id.battle_headId)      ImageView mIvHead;
    @Bind(R.id.tv_timeCountId)      TextView mTvTimeCount;
    @Bind(R.id.pointer)             SpeedPointer mPointer;
    @Bind(R.id.dangradview)         DrawDangradView mDangrad;
    @Bind(R.id.tv_battery_Id)       TextView mTvBattery;
    @Bind(R.id.ble_conn)            TextView mTvConn;

    private BattleViewModel _viewModel;
    private int             _rememberCurrSpeedLevel = 0;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,BattleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected Class<? extends BleProfileService> getServiceClass() {
        return HtsService.class;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_battle);
        ButterKnife.bind(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,new IntentFilter(HtsService.BROADCAST_HTS_MEASUREMENT));
        _viewModel = new BattleViewModel(this);
        initView();
    }

    private void initView() {
        mPointer.setOnlistenSpeed(this);
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
                mTvConn.setText(R.string.connected_text);
                break;
            case BleConstant.STATE_DISCONNECTED:
                mTvConn.setText(R.string.dis_connect_text);
                break;
        }
    }

    @Override
    protected UUID[] getFilterUUID() {
        return new UUID[]{BleUUID.TP_SERVICE_UUID};
    }

    /**
     * 接收蓝牙数据广播
     */
    private BroadcastReceiver _receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (HtsService.BROADCAST_HTS_MEASUREMENT.equals(intent.getAction())) {
                final float value = intent.getFloatExtra(HtsService.EXTRA_HTS,0.0f);
                runOnUiThread(() -> {

                });
            }
        }
    };

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
                LastSpeedActivity.start(this);
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
}
