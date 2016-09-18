package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.model.MyMessageModel;
import com.zf.weisport.presenter.IMyNewsView;
import com.zf.weisport.presenter.biz.IMyNewsBiz;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 09:40
 * @email Xs.lin@foxmail.com
 */
public class MyNewsBizImpl extends BaseBiz<IMyNewsView> implements IMyNewsBiz{

    private int _pageSize = 100;

    public MyNewsBizImpl(IMyNewsView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void loadInitData() {
        getView().setPageIndex(1);//默认 页码为1
    }

    @Override
    public void getMyMessage() {
        String pageIndex = String.valueOf(getView().getPageIndex());
        String pageSize  = String.valueOf(_pageSize);
        addSubscription(
            RequestHelper.getInstance().getMyMessage(User.getUser().getId(),pageIndex,pageSize).
                subscribe(new Subscriber<MyMessageModel>() {
                    @Override
                    public void onCompleted() {
                        getView().onFinishRefreshView();
                    }
                    @Override
                    public void onError(Throwable e) {
                        showToast(R.string.net_error_toast);
                        getView().onFinishRefreshView();
                        getView().onNetErrorNotifyUI();
                    }

                    @Override
                    public void onNext(MyMessageModel myMessageModel) {
                        if (myMessageModel.isSuccess()) {
                            if (myMessageModel.isEmptyData())
                                getView().onNetEmptyNotifyUI();
                            else
                                getView().onGetMyMessageCompleted(myMessageModel.getData());
                        } else {
                            showToast(myMessageModel.getErrMsg());
                        }
                    }
                })
        );
    }

    @Override
    public void getMoreMyMessage() {
        int currentPageIndex = getView().getPageIndex();
        if (currentPageIndex >= _pageSize) {
//            showToast(R.string.no_more_data_text);
            getView().onFinishRefreshView();
            return;
        }
        getView().setPageIndex(++currentPageIndex);
        String pageIndex = String.valueOf(getView().getPageIndex());
        String pageSize = String.valueOf(_pageSize);
        addSubscription(
                RequestHelper.getInstance().getMyMessage(User.getUser().getId(),pageIndex,pageSize)
                .subscribe(new Subscriber<MyMessageModel>() {
                    @Override
                    public void onCompleted() {
                        getView().onFinishRefreshView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(R.string.net_error_toast);
                        getView().onFinishRefreshView();
                        int currentPageIndex = getView().getPageIndex();
                        getView().setPageIndex(--currentPageIndex);
                    }

                    @Override
                    public void onNext(MyMessageModel myMessageModel) {
                        if (myMessageModel.isSuccess()) {
                            if (myMessageModel.isEmptyData()) {
//                                showToast(R.string.no_more_data_text);
                                int currentPageIndex = getView().getPageIndex();
                                getView().setPageIndex(--currentPageIndex);
                            } else
                                getView().onGetMyMessageCompleted(myMessageModel.getData());
                        }else {
                            showToast(myMessageModel.getErrMsg());
                            int currentPageIndex = getView().getPageIndex();
                            getView().setPageIndex(--currentPageIndex);
                        }
                    }
                })
        );
    }

    @Override
    public void readMessage(int position,MyMessageModel myMessageModel) {
        showLoadingView();
        addSubscription(
                RequestHelper.getInstance().requestReadMessage(User.getUser().getId(),myMessageModel.getID()).
                        doOnNext(baseModel -> {
                            if (baseModel.isSuccess())
                                getView().onReadMessageCompleted(position, myMessageModel);
                            else
                                showToast(baseModel.getErrMsg());
                        }).subscribe(getSubscriber())
        );
    }
}
