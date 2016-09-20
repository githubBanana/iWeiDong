package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.AddLabelModel;
import com.zf.weisport.model.GetLabelModel;
import com.zf.weisport.presenter.ITopicLabelShowView;
import com.zf.weisport.presenter.biz.ITopicLabelShowBiz;
import com.zf.weisport.presenter.biz.impl.TopicLabelShowBizImpl;
import com.zf.weisport.ui.callback.ITopicLabelShowCallback;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 14:02
 * @email Xs.lin@foxmail.com
 */
public class TopicLabelShowViewModel extends BaseViewModel<ITopicLabelShowCallback,GetLabelModel> implements ITopicLabelShowView {

    public String labelContent;

    private ITopicLabelShowBiz biz;
    public TopicLabelShowViewModel(ITopicLabelShowCallback iTopicLabelShowCallback) {
        super(iTopicLabelShowCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new TopicLabelShowBizImpl(this,this);
    }

    /**
     * 获取话题标签列表
     */
    public void getTopicLabelList() {
        biz.getTopicLabelList();
    }

    public void newLabel() {
        biz.newLabel();
    }
    @Override
    public void onGetTopicLabelListCompleted(List<GetLabelModel> getLabelModels) {
        getCallback().onGetTopicLabelListSuccess(getLabelModels);
    }

    @Override
    public void onNewLabelCompleted(AddLabelModel addLabelModel) {
        getCallback().onNewLabelSuccess(addLabelModel);
    }

    @Override
    public void setLabelContent(String labelContent) {
        this.labelContent = labelContent;
    }

    @Override
    public String getLabelContent() {
        return this.labelContent;
    }
}
