package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zf.weisport.R;
import com.zf.weisport.ui.activity.base.BaseActivity;

/**
 * @version V1.0 <最近一次转速记录>
 * @author: Xs
 * @date: 2016-09-21 17:41
 * @email Xs.lin@foxmail.com
 */
public class LastSpeedActivity extends BaseActivity {

    private RelativeLayout chart,y_zhou;
    private TextView mLeave,mMaxSpeed,mStartRecord,mEndRecord;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,LastSpeedActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_last_speed);
        initView();
        initData();
    }

    private void initView() {
        mLeave = (TextView) findViewById(R.id.speed_leave_titleId);
        mMaxSpeed = (TextView) findViewById(R.id.tv_speedRecord_maxValue_Id);
        mStartRecord = (TextView) findViewById(R.id.tv_start_record_Id);
        mEndRecord = (TextView) findViewById(R.id.tv_end_record_Id);
        chart= (RelativeLayout) findViewById(R.id.chart);
        y_zhou = (RelativeLayout) findViewById(R.id.y_zhou);
        mLeave.setOnClickListener(view -> finish());
    }

    private void initData() {
          /*  if (RealTimeBag.getBag().getSpeedList() != null && RealTimeBag.getBag().getMaxSpeed() <= DrawY.MAX_Y) {
                List<Integer> listsY = new ArrayList<>();// speed list
                listsY = RealTimeBag.getBag().getSpeedList();
                int maxSpeed = RealTimeBag.getBag().getMaxSpeed();
                DrawY _mDrawY = new DrawY(this);
                _mDrawY.setY(maxSpeed);//设置最大转速
                DrawSpeedRecord _mDrawSpeed = new DrawSpeedRecord(this, listsY);
                _mDrawSpeed.setY(maxSpeed);//设置最大转速
                _mDrawSpeed.setX(listsY.size() - 1);//间隔数
                y_zhou.addView(_mDrawY);
                chart.addView(_mDrawSpeed);

                mMaxSpeed.setText(RealTimeBag.getBag().getMaxSpeed() + "转");
                mStartRecord.setText(RealTimeBag.getBag().getStartTime());
                mEndRecord.setText(RealTimeBag.getBag().getEndTime());
                for (int i = 0; i < listsY.size(); i++) {
                    Log.e("tes","getSpeedList(: "+listsY.get(i));
                }
            }*/
    }

    public int getRandom(int min,int max){
        return (int) Math.round(Math.random()*(max-min)+min);
    }


}
