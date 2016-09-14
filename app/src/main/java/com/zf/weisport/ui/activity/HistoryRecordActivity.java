package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zf.weisport.R;
import com.zf.weisport.adapter.ViewPaperAdapter;
import com.zf.weisport.databinding.ActivityHistoryRecordBinding;
import com.zf.weisport.manager.util.UnixTimeStamp;
import com.zf.weisport.manager.widget.SelectDeviceDialog;
import com.zf.weisport.manager.widget.speedchart.DrawSpeedRecordPortRait;
import com.zf.weisport.manager.widget.speedchart.DrawYPortRait;
import com.zf.weisport.model.StatisticsModel;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.IHistoryRecordCallback;
import com.zf.weisport.ui.viewmodel.HistoryRecordViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;

/**
 * @version V1.0 <历史转速记录界面 注：此处暂时只有“转速”这一项，“时长”被屏蔽>
 * @author: Xs
 * @date: 2016-09-14 09:17
 * @email Xs.lin@foxmail.com
 */
public class HistoryRecordActivity extends BaseActivity<HistoryRecordViewModel,ActivityHistoryRecordBinding>
implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener,
        SelectDeviceDialog.OnListenSelectDevice,IHistoryRecordCallback{

    private int[]               textIDs         = {R.id.history_top_tv_hour, R.id.history_top_tv_speed};
    private int[]               linearLayoutIDs = {R.id.history_top_layout_hour, R.id.history_top_layout_speed};
    private RelativeLayout[]    mLinearLayouts  = new RelativeLayout[linearLayoutIDs.length];
    private TextView[]          mTextViews      = new TextView[textIDs.length];
    private RelativeLayout      mSpeedDrawRelayout,mYzhouRelayout;
    private DrawSpeedRecordPortRait _mDrawSpeed;
    private DrawYPortRait _mDrawY;
    private MenuItem            mMenuItem;
    private PagerAdapter        mPagerApdapter;
    private int                 curViewPaperIndex;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,HistoryRecordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected HistoryRecordViewModel initViewModel() {
        return new HistoryRecordViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setHistoryrecordViewModel(getViewModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record,true);
        ButterKnife.bind(this);
        initView();
        /**
         * 获取历史数据
         */
        getViewModel().getHistoryRecordData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.text_speed_record);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_record_menu,menu);
        mMenuItem = menu.findItem(R.id.category_text).setCheckable(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.category_arrow)
            SelectDeviceDialog.getInstance().show(getSupportFragmentManager(),SelectDeviceDialog.TAG);
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        //获取 viewpaper top 布局资源
        for (int i = 0; i < mLinearLayouts.length; i++) {
            mLinearLayouts[i] = (RelativeLayout) findViewById(linearLayoutIDs[i]);
            mLinearLayouts[i].setEnabled(true);
            mLinearLayouts[i].setOnClickListener(new ViewPageTopOnClickListener());
            mLinearLayouts[i].setTag(i);
            mTextViews[i] = (TextView) findViewById(textIDs[i]);
        }
        View viewHour = getLayoutInflater().inflate(R.layout.layout_hourlength,null);
        View viewSpeed = getLayoutInflater().inflate(R.layout.layout_speed,null);
        mSpeedDrawRelayout = (RelativeLayout) viewSpeed.findViewById(R.id.speed_draw);
        mYzhouRelayout = (RelativeLayout) viewSpeed.findViewById(R.id.y_zhou);
        List<View> views = new ArrayList<>();
//        views.add(viewHour);
        views.add(viewSpeed);

        mPagerApdapter = new ViewPaperAdapter(views);
        getBinding().viewpager.setAdapter(mPagerApdapter);
        getBinding().viewpager.setVisibility(View.VISIBLE);
        getBinding().viewpager.setOnPageChangeListener(this);
        //default setting
        getBinding().viewpager.setCurrentItem(curViewPaperIndex);
        mLinearLayouts[curViewPaperIndex+1].setEnabled(false);
        mTextViews[curViewPaperIndex+1].setEnabled(false);

        final RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.rg);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.rb_week);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setViewPageIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        final RadioButton b = (RadioButton) findViewById(i);
        int type;
        if (getString(R.string.text_week).equals(b.getText()))
            type = 1;
        else if (getString(R.string.text_month).equals(b.getText()))
            type = 2;
        else
            type = 3;
        getViewModel().setType(String.valueOf(type));
        if (getViewModel().isNeedToAccessNet(type))
            getViewModel().getHistoryRecordData();
        else
            toDraw(getViewModel().getCurrentList(type));
    }

    @Override
    public void onGetHistoryRecordDataSuccess(List<StatisticsModel> statisticsModel) {
        toDraw(statisticsModel);
    }

    private final String TAG = HistoryRecordActivity.class.getSimpleName();
    @Override
    public void OnListenDeviceCallBack(String sport, int selectItem) {
        mMenuItem.setTitle(sport);
        String deviceType = String.valueOf(selectItem);
        if (!getViewModel().getDeviceType().equals(deviceType)) {
            getViewModel().clearModelList();
            getViewModel().setDeviceType(deviceType);
            getViewModel().getHistoryRecordData();
        }
    }


    class ViewPageTopOnClickListener implements View.OnClickListener {//监听viewpaper top

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == linearLayoutIDs[0] || id == linearLayoutIDs[1] || id == linearLayoutIDs[2] || id == linearLayoutIDs[3]) {
                int index = (Integer) v.getTag();
                setViewPageIndex(index);
            }
        }

    }

    /**
     * 切换viewpager（暂时不需要）
     * @param index
     */
    private void setViewPageIndex(int index) { //切换页
        if (index < 0) {
            index = 0;
        } else if (index > linearLayoutIDs.length - 1) {
            index = linearLayoutIDs.length - 1;
        }
        index = index % linearLayoutIDs.length;
        if (curViewPaperIndex != index) {
            mLinearLayouts[curViewPaperIndex].setEnabled(true);
            mTextViews[curViewPaperIndex].setEnabled(true);
            curViewPaperIndex = index;
            mLinearLayouts[curViewPaperIndex].setEnabled(false);
            mTextViews[curViewPaperIndex].setEnabled(false);
            getBinding().viewpager.setCurrentItem(curViewPaperIndex);
        }
    }

    /**
     * 绘制曲线
     * @param temps
     */
    private int x ;
    private int maxSpeed ;
    private long duration ;
    private void toDraw(List<StatisticsModel> temps){

        List<Integer> listsX = new ArrayList<>();//线性图  范围10-100
        List<Integer> listsY = new ArrayList<>();
        x = 0;
        maxSpeed = 0;
        duration = 0;
        Observable.just("").doOnNext(s -> {
            for (int i = 0; i < temps.size(); i++) {
                int speed = Integer.valueOf(temps.get(i).getSpeed());
                listsY.add(speed);
                listsX.add(x);
                if (speed > maxSpeed)
                    maxSpeed = speed;
                duration = duration + Integer.decode(temps.get(i).getDuration());
            }
        }).subscribe(s -> {
            mYzhouRelayout.removeAllViews();
            mSpeedDrawRelayout.removeAllViews();
            String startTimeStr = UnixTimeStamp.stampToNormal(Long.decode(temps.get(0).getTime()), UnixTimeStamp.Type.FORMAT3);
            String endTimeStr = UnixTimeStamp.stampToNormal(Long.decode(temps.get(temps.size() - 1).getTime()),UnixTimeStamp.Type.FORMAT3);
            Log.e(TAG, "toDraw: "+startTimeStr+"  "+endTimeStr );
            getViewModel().setStartTime(startTimeStr);
            getViewModel().setEndTime(endTimeStr);
            getViewModel().setAccumulateHours(UnixTimeStamp.stampToNormalStandard(duration));
            getViewModel().setMaxSpeed(""+maxSpeed);
            getViewModel().notifyUIChange();

            _mDrawY = new DrawYPortRait(HistoryRecordActivity.this);
            _mDrawY.setY(maxSpeed);//设置最大转速
            mYzhouRelayout.addView(_mDrawY);
            _mDrawSpeed = new DrawSpeedRecordPortRait(HistoryRecordActivity.this, listsX,listsY);
            _mDrawSpeed.setY(maxSpeed);//设置最大转速
            _mDrawSpeed.setX(temps.size()-1);//点数
            mSpeedDrawRelayout.addView(_mDrawSpeed);
        });
    }


}
