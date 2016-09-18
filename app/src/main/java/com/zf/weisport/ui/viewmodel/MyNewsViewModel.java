package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.MyMessageModel;
import com.zf.weisport.presenter.IMyNewsView;
import com.zf.weisport.presenter.biz.IMyNewsBiz;
import com.zf.weisport.presenter.biz.impl.MyNewsBizImpl;
import com.zf.weisport.ui.callback.IMyNewsCallback;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 09:18
 * @email Xs.lin@foxmail.com
 */
public class MyNewsViewModel extends BaseViewModel<IMyNewsCallback,MyMessageModel> implements IMyNewsView{

    public int _pageIndex;

    private IMyNewsBiz biz;

    public MyNewsViewModel(IMyNewsCallback iMyNewsCallback) {
        super(iMyNewsCallback);
        biz = getBiz();
        biz.loadInitData();
    }

    /**
     * 获取我的消息
     */
    public void getMyMessage() {
        biz.getMyMessage();
    }

    /**
     * 获取更多我的消息
     */
    public void getMoreMyMessage() {
        biz.getMoreMyMessage();
    }

    /**
     * 读信息
     * @param myMessageModel
     */
    public void readMessage(int positin,MyMessageModel myMessageModel) {
        biz.readMessage(positin,myMessageModel);
    }

    @Override
    protected BaseBiz createBiz() {
        return new MyNewsBizImpl(this,this);
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this._pageIndex = pageIndex;
    }

    @Override
    public int getPageIndex() {
        return this._pageIndex;
    }

    @Override
    public void onGetMyMessageCompleted(List<MyMessageModel> myMessageModels) {
        getCallback().onGetMyMessageSuccess(myMessageModels);
    }

    @Override
    public void onGetMoreMyMessageCompleted(List<MyMessageModel> myMessageModels) {
        getCallback().onGetMoreMyMessageSuccess(myMessageModels);
    }

    @Override
    public void onReadMessageCompleted(int position,MyMessageModel myMessageModel) {
        getCallback().onReadMessageSuccess(position,myMessageModel);
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
    public void onFinishRefreshView() {
        getCallback().onFinishRefreshView();
    }


}
