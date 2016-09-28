package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.diy.diylibrary.widget.dialog.ShareDialog;
import com.zf.weisport.R;
import com.zf.weisport.ui.activity.base.BaseActivity;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 17:57
 * @email Xs.lin@foxmail.com
 */
public class BattleResultActivity extends BaseActivity
implements View.OnClickListener,ShareDialog.OnListenShareDialog{

    public static String PERCENT = "Percent";
    public static String GAMEID = "GameId";
    private String percent ;

    public static void start(Activity activity,String Percent,String GameId) {
        Intent intent = new Intent(activity,BattleResultActivity.class);
        intent.putExtra(PERCENT,Percent);
        intent.putExtra(GAMEID, GameId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_battle_result);
        initView();
    }

    private void initView() {
        findViewById(R.id.battle_result_share_btn_Id).setOnClickListener(this);
        findViewById(R.id.btn_battle_done_Id).setOnClickListener(this);
        TextView mTvPercent = (TextView) findViewById(R.id.tv_percent_Id);
        percent = getIntent().getStringExtra(PERCENT)+"%";
        String gameId = getIntent().getStringExtra(GAMEID);
        mTvPercent.setText(percent+"玩家");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.battle_result_share_btn_Id:
                ShareDialog shareDialog = ShareDialog.getInstance("我打败了全国"+percent+"的人，你敢来挑战吗?",WebActivity.INSTALL_URL);
                shareDialog.show(getSupportFragmentManager(),"share");
                break;
            case R.id.btn_battle_done_Id:
                finish();
                break;
        }
    }

    @Override
    public void OnListenTouch(String platform) {

    }
}
