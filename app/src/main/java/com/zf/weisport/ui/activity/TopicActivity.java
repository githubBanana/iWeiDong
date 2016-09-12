package com.zf.weisport.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.fragment.TopicFragment;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-12 16:29
 * @email Xs.lin@foxmail.com
 */
public class TopicActivity extends BaseActivity {

    public static final String ACTION_ALL_TOPIC = "ACTION_ALL_TOPIC";
    public static final String ACTION_MY_TOPIC = "ACTION_MY_TOPIC";

    private static final String EXTRA_ID = "id";
    private static final String EXTRA_TITLE = "title";


    /**
     * 话题列表
     *
     * @param context
     * @param id
     * @param title
     */
    public static void startActivity(Context context, String id, String title) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.setAction(ACTION_ALL_TOPIC);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    /**
     * 我的话题
     *
     * @param context
     */
    public static void startActivityForMyTopic(Context context) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.setAction(ACTION_MY_TOPIC);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic,true);

        String action = getIntent().getAction();

        int type = 1;//请求类型（1-标签 2-收藏 3-用户发布）
        String id;
        switch (action) {
            case ACTION_ALL_TOPIC:
                type = 1;
//                getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));
                setCenterTitle(getIntent().getStringExtra(EXTRA_TITLE));
                id = getIntent().getStringExtra(EXTRA_ID);
                break;
            case ACTION_MY_TOPIC:
                type = 3;
                setCenterTitle("我的话题");
                id = String.valueOf(User.getUser().getId());
                break;
            default:
                id = "0";
                break;
        }

        TopicFragment fragment = TopicFragment.newInstance(this, id, type);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }
}
