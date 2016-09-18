package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TabHost;

import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.fragment.FollowFragment;
import com.zf.weisport.ui.fragment.TopicFragment;

/**
 * @version V1.0 <关注 & 收藏 界面>
 * @author: Xs
 * @date: 2016-09-18 14:12
 * @email Xs.lin@foxmail.com
 */
public class FollowActivity extends BaseActivity {

    public static void start(Activity activity) {
        Intent intent =new Intent(activity,FollowActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow,true);
        TabHost mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(getString(R.string.text_follow_friends)).setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(getString(R.string.text_dynamic_collection)).setContent(R.id.tab2));

        FollowFragment followFragment = new FollowFragment();
        Fragment topicFragment = TopicFragment.newInstance(this, User.getUser().getId(), 2);
        getSupportFragmentManager().beginTransaction().replace(R.id.tab1,followFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.tab2,topicFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.tv_follow);
    }
}
