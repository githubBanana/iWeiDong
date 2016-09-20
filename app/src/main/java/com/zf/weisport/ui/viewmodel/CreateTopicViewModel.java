package com.zf.weisport.ui.viewmodel;

import com.diy.labelview.Tag;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.R;
import com.zf.weisport.manager.event.Event;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.AddLabelModel;
import com.zf.weisport.model.AddTopicModel;
import com.zf.weisport.presenter.ICreateTopicView;
import com.zf.weisport.presenter.biz.ICreateTopicBiz;
import com.zf.weisport.presenter.biz.impl.CreateTopicBizImpl;
import com.zf.weisport.ui.callback.ICreateTopicCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 11:46
 * @email Xs.lin@foxmail.com
 */
public class CreateTopicViewModel extends BaseViewModel<ICreateTopicCallback,AddTopicModel> implements ICreateTopicView{

    private ICreateTopicBiz biz;
    public List<Event>      labelList;//存放话题标签
    public String           topicContent;//话题内容
    public List<String>     img_IDs ; //存放图片ID
    public List<String>     filePaths;//存放本地图片路径

    public CreateTopicViewModel(ICreateTopicCallback iCreateTopicCallback) {
        super(iCreateTopicCallback);
        biz = getBiz();
        labelList = new ArrayList<>();
        img_IDs = new ArrayList<>();
        filePaths = new ArrayList<>();
    }

    @Override
    protected BaseBiz createBiz() {
        return new CreateTopicBizImpl(this,this);
    }

    /**
     * 发布话题
     */
    public void release() {
        biz.release();
    }



    @Override
    public void onReleaseCompleted(AddTopicModel addTopicModel) {
        getCallback().onReleaseSuccess(addTopicModel);
    }

    @Override
    public void setLabelList(List<Event> labelList) {
        this.labelList = labelList;
    }

    @Override
    public List<Event> getLabelList() {
        return this.labelList;
    }

    @Override
    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    @Override
    public String getTopicContent() {
        return this.topicContent;
    }

    @Override
    public void setImg_IDs(List<String> img_iDs) {
        this.img_IDs = img_iDs;
    }

    @Override
    public List<String> getImg_IDs() {
        return this.img_IDs;
    }

    @Override
    public void addImg_ID(int position,String ID) {
        this.img_IDs.add(position,ID);
    }

    @Override
    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    @Override
    public List<String> getFilePaths() {
        return this.filePaths;
    }

    /**
     * 添加话题标签
     * @param mList
     * @return
     */
    public void addLabel(List<Event> mList) {
        List<Tag> mTags = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            Event event = mList.get(i);
            Tag tag = new Tag();
            if (i >= 1) {
                tag.setId(event.getAddLabelModel().ID);
                tag.setDeleteMode(true);
            } else {
                tag.setId("xxx");
            }
            tag.setLocation(i);
            tag.setChecked(false);
            tag.setTitle("#" + event.getAddLabelModel().Name);
            mTags.add(tag);
        }
        getCallback().addLabelListSuccess(mTags);
    }

    /**
     * new 第一个话题标签（不可删除标签）
     * @return
     */
    public void newFirstLable() {
        Event event=new Event();
        AddLabelModel addLabelModel = new AddLabelModel();
        addLabelModel.Name= UIUtil.getString(R.string.topicLable);
        event.setAddLabelModel(addLabelModel);
        this.labelList.add(event);
        addLabel(labelList);
    }

    /**
     * 添加话题标签（可删除）
     * @param event
     */
    public void addLabelEventModel(Event event) {
        this.labelList.add(event);
        addLabel(labelList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        labelList = null;
        img_IDs = null;
        filePaths = null;
    }
}
