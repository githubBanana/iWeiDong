package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zf.weisport.R;
import com.zf.weisport.ui.fragment.MeFragment;
import com.zf.weisport.ui.fragment.SportFragment;
import com.zf.weisport.ui.fragment.SquareFragment;
import com.zf.weisport.ui.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private long exitTime = 0;

    public static void  start(Activity activity) {
        Intent intent = new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        mTabHost.getTabWidget().setShowDividers(0);
        mTabHost.addTab(mTabHost.newTabSpec(getTagString(R.string.mainTab_sport)).setIndicator(getTabLayoutView(R.layout.tabs_sport_layout)), SportFragment.class,null);
        mTabHost.addTab(mTabHost.newTabSpec(getTagString(R.string.mainTab_square)).setIndicator(getTabLayoutView(R.layout.tabs_square_layout)), SquareFragment.class,null);
        mTabHost.addTab(mTabHost.newTabSpec(getTagString(R.string.mainTab_video)).setIndicator(getTabLayoutView(R.layout.tabs_video_layout)), VideoFragment.class,null);
        mTabHost.addTab(mTabHost.newTabSpec(getTagString(R.string.mainTab_me)).setIndicator(getTabLayoutView(R.layout.tabs_me_layout)), MeFragment.class,null);

    }

    private String getTagString(int stringId) {
        return getResources().getString(stringId);
    }
    private View getTabLayoutView(int layoutId) {
        View view =  getLayoutInflater().inflate(layoutId,null);
        return view;
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),R.string.switch_to_Backstage, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }
}
