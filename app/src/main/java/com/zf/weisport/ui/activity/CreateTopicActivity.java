package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import com.cy.src.photos.images.SelectImageFragment;
import com.diy.labelview.Tag;
import com.diy.labelview.TagListView;
import com.diy.labelview.TagView;
import com.zf.weisport.R;
import com.zf.weisport.databinding.ActivityCreateTopicBinding;
import com.zf.weisport.manager.event.Event;
import com.zf.weisport.manager.event.NotifyEvent;
import com.zf.weisport.model.AddTopicModel;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.ICreateTopicCallback;
import com.zf.weisport.ui.viewmodel.CreateTopicViewModel;

import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * @version V1.0 <创建话题界面>
 * @author: Xs
 * @date: 2016-09-20 09:47
 * @email Xs.lin@foxmail.com
 */
public class CreateTopicActivity extends BaseActivity<CreateTopicViewModel,ActivityCreateTopicBinding>
        implements TagListView.OnTagClickListener,TagListView.OnTagRemoveListener,ICreateTopicCallback{

    private SelectImageFragment mFmSelectImg;
    public static void start(Activity activity) {
        Intent intent = new Intent(activity,CreateTopicActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected CreateTopicViewModel initViewModel() {
        return new CreateTopicViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setCreateTopicViewModel(getViewModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic,true);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mFmSelectImg = new SelectImageFragment();
        mFmSelectImg.setAllSelectImagesCount(6).setMultiSelect(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_select_img,mFmSelectImg).commit();
        getBinding().etContentId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getViewModel().setTopicContent(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        initLabel();
    }

    private void initLabel() {
        getBinding().tagview.setTagColor(R.color.green);
        getBinding().tagview.setDeleteMode(true);
        getBinding().tagview.setOnTagClickListener(this);
        getBinding().tagview.setOnTagRemoveListener(this);

        getViewModel().newFirstLable();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.create_topic_text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_topic_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.release_menuId) {
            getViewModel().setFilePaths(mFmSelectImg.getSelectImages());
            getViewModel().release();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTagClick(TagView tagView, Tag tag) {
        if (tag.getId().equals("xxx")) {
            TopicLabelShowActivity.start(this);
        }
        else {
            Iterator<Event> iterator = getViewModel().getLabelList().iterator();
            while (iterator.hasNext()){
                Event next = iterator.next();
                if (TextUtils.equals(next.getAddLabelModel().ID,tag.getId())){
                    iterator.remove();
                }
            }
            getBinding().tagview.removeTag(tag);
        }
    }

    @Override
    public void onRemoveTag(List<Tag> list) {

    }

    @Override
    public void addLabelListSuccess(List<Tag> mTags) {
        getBinding().tagview.removeAllViews();
        getBinding().tagview.setTags(mTags);
    }

    @Override
    public void onReleaseSuccess(AddTopicModel addTopicModel) {
        finish();
    }

    @Subscribe
    public void onEvent(Event event) {
        switch (event.getFlag()) {
            case NotifyEvent.ADD_TOPIC_ALBEL:
                if (getViewModel().getLabelList().contains(event)){
                    return;
                }
                getViewModel().addLabelEventModel(event);
                break;
        }
    }

}
