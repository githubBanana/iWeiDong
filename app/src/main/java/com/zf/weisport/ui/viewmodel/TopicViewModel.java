package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.TopicModel;
import com.zf.weisport.presenter.ITopicView;
import com.zf.weisport.presenter.biz.ITopicBiz;
import com.zf.weisport.presenter.biz.impl.TopicBizImpl;
import com.zf.weisport.ui.callback.ITopicCallback;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 17:25
 * @email Xs.lin@foxmail.com
 */
public class TopicViewModel extends BaseViewModel<ITopicCallback,TopicModel> implements ITopicView {

    public int      type;//请求类型
    public String   objType;//请求类型对应的ID
    public int   pageIndex;//页码

    private ITopicBiz biz;
    public TopicViewModel(ITopicCallback iTopicCallback) {
        super(iTopicCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new TopicBizImpl(this,this);
    }

    /**
     * 获取话题列表
     */
    public void getTopicList() {
        biz.getTopic();
    }

    /**
     * 获取更多话题列表
     */
    public void getMoreTopicList() {
        biz.getMoreTopic();
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
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override
    public void onGetTopicCompleted(List<TopicModel> topicModels) {
        getCallback().onGetTopicSuccess(topicModels);
    }

    @Override
    public void onGetMoreTopicCompleted(List<TopicModel> topicModels) {
        getCallback().onGetMoreTopicSuccess(topicModels);
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
