package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.TopicModel;
import com.zf.weisport.presenter.ITopicView;
import com.zf.weisport.presenter.biz.ITopicBiz;
import com.zf.weisport.presenter.biz.impl.TopicImpl;
import com.zf.weisport.ui.callback.ITopicCallback;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 17:25
 * @email Xs.lin@foxmail.com
 */
public class TopicViewModel extends BaseViewModel<ITopicCallback,TopicModel> implements ITopicView {

    public int type;//请求类型
    public String objType;//请求类型对应的ID

    private ITopicBiz biz;
    public TopicViewModel(ITopicCallback iTopicCallback) {
        super(iTopicCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new TopicImpl(this,this);
    }

    /**
     * 获取话题列表
     */
    public void getTopicList() {
        biz.getTopic();
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public void setObjType(String objType) {
        this.objType = objType;
    }

    @Override
    public String getObjType() {
        return this.objType;
    }

    @Override
    public void onGetTopicCompleted(List<TopicModel> topicModels) {
        getCallback().onGetTopicSuccess(topicModels);
    }

    @Override
    public void onNetErrorNotifyUI() {
        getCallback().onNetError();
    }

    @Override
    public void onNetEmptyNotifyUI() {
        getCallback().onNetEmpty();
    }

}
