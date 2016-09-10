package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.model.BaseModel;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.GetTopModel;
import com.zf.weisport.model.LabelTopicModel;
import com.zf.weisport.presenter.ISquareView;
import com.zf.weisport.presenter.biz.ISquareBiz;
import com.zf.weisport.presenter.biz.impl.SquareBizImpl;
import com.zf.weisport.ui.callback.ISquareCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 09:28
 * @email Xs.lin@foxmail.com
 */
public class SquareViewModel extends BaseViewModel<ISquareCallback,BaseModel> implements ISquareView{

    private List<HashMap<String,Object>>    topList;
    private List<LabelTopicModel>           labelTopicList;

    private ISquareBiz biz;

    public SquareViewModel(ISquareCallback iSquareCallback) {
        super(iSquareCallback);
        this.topList        = new ArrayList<>();
        this.labelTopicList = new ArrayList<>();
        this.biz                 = getBiz();
    }

    /**
     * 获取轮播图
     */
    public void getTop() {
        biz.getTop();
    }

    /**
     * 获取话题标签
     */
    public void getLabelTopic() {
        biz.getLabelTopic();
    }

    @Override
    protected BaseBiz createBiz() {
        return new SquareBizImpl(this,this);
    }

    public List<HashMap<String, Object>> getTopList() {
        return topList;
    }

    public void setTopList(List<HashMap<String, Object>> topList) {
        this.topList = topList;
    }

    public List<LabelTopicModel> getLabelTopicList() {
        return labelTopicList;
    }

    public void setLabelTopicList(List<LabelTopicModel> labelTopicList) {
        this.labelTopicList = labelTopicList;
    }

    @Override
    public void onGetLabelTopicCompleted(List<LabelTopicModel> labelTopicModels) {
        setLabelTopicList(labelTopicModels);
        getCallback().onGetLableTopicSuccess();
    }

    @Override
    public void onGetTopCompleted(List<GetTopModel> getTopModels) {
        topList.clear();
        for (int i = 0; i < getTopModels.size(); i++) {
            HashMap map = new HashMap();
            map.put("ID",getTopModels.get(i).getID());
            map.put("url",getTopModels.get(i).getUrl());
            map.put("filePath",getTopModels.get(i).getFilePath());
            map.put("title",getTopModels.get(i).getTitle());
            topList.add(map);
        }
        getCallback().onGetTopSuccess();
    }

    @Override
    public void onNetErrorNotifyUI() {
        getCallback().onNetError();
    }

    @Override
    public void onNetEmptyNotifyUI() {
        getCallback().onNetEmpty();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        labelTopicList  = null;
        topList         = null;
    }
}
