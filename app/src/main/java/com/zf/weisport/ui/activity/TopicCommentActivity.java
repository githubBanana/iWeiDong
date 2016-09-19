package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.zf.weisport.R;
import com.zf.weisport.model.TopicModel;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.fragment.TopicCommentFragment;

/**
 * @version V1.0 <话题评论详情>
 * @author: Xs
 * @date: 2016-09-19 10:25
 * @email Xs.lin@foxmail.com
 */
public class TopicCommentActivity extends BaseActivity {

    public static void start(Activity activity, TopicModel model,int position) {
        Intent intent = new Intent(activity,TopicCommentActivity.class);
        intent.putExtra("data", (Parcelable) model);
        intent.putExtra("position",position);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_comment,true);

        TopicModel mTopicModel = getIntent().getParcelableExtra("data");

        Fragment fragment = TopicCommentFragment.newInstance(this, mTopicModel.getID(),getIntent().getIntExtra("position",0));
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.text_detail);
    }
}
