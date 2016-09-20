package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;

import com.zf.weisport.R;
import com.zf.weisport.adapter.LabelShowAdapter;
import com.zf.weisport.databinding.ActivityTopicLabelShowBinding;
import com.zf.weisport.manager.event.Event;
import com.zf.weisport.manager.event.NotifyEvent;
import com.zf.weisport.model.AddLabelModel;
import com.zf.weisport.model.GetLabelModel;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.ITopicLabelShowCallback;
import com.zf.weisport.ui.viewmodel.TopicLabelShowViewModel;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @version V1.0 <话题标签列表>
 * @author: Xs
 * @date: 2016-09-20 13:39
 * @email Xs.lin@foxmail.com
 */
public class TopicLabelShowActivity extends BaseActivity<TopicLabelShowViewModel,ActivityTopicLabelShowBinding>
        implements ITopicLabelShowCallback{

    private LabelShowAdapter mAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,TopicLabelShowActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected TopicLabelShowViewModel initViewModel() {
        return new TopicLabelShowViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setTopicLabelShowViewModel(getViewModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_label_show,true);
        initView();
        getViewModel().getTopicLabelList();
    }

    private void initView() {
        getBinding().recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new LabelShowAdapter((view, getLabelModel, position) -> {
            AddLabelModel model = new AddLabelModel();
            model.ID = getLabelModel.ID;
            model.Name = getLabelModel.Name;
            sendLabelEvent(NotifyEvent.ADD_TOPIC_ALBEL,model);
        });
        getBinding().recyclerview.setAdapter(mAdapter);
        getBinding().etInputLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getViewModel().setLabelContent(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        getBinding().btnNewLabel.setOnClickListener(view -> getViewModel().newLabel());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.topic_lable_add);
    }

    @Override
    public void onGetTopicLabelListSuccess(List<GetLabelModel> getLabelModels) {
        mAdapter.setData(getLabelModels);
    }

    @Override
    public void onNewLabelSuccess(AddLabelModel addLabelModel) {
        sendLabelEvent(NotifyEvent.ADD_TOPIC_ALBEL,addLabelModel);
    }

    private void sendLabelEvent(int flag,AddLabelModel a) {
        Event event = new Event();
        event.setFlag(flag);
        event.setAddLabelModel(a);
        EventBus.getDefault().post(event);
        finish();
    }
}
