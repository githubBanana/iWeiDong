package com.zf.weisport.presenter.biz.impl;

import android.util.Log;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.model.GetVideoModel;
import com.zf.weisport.presenter.IVideoView;
import com.zf.weisport.presenter.biz.IVideoBiz;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 16:21
 * @email Xs.lin@foxmail.com
 */
public class VideoBizImpl extends BaseBiz<IVideoView> implements IVideoBiz {

    public VideoBizImpl(IVideoView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getVideo() {
        addSubscription(
                RequestHelper.getInstance().getVideo(getView().getType(),getView().getPageIndex()).
                        subscribe(new Subscriber<GetVideoModel>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().onNetErrorNotifyUI();
                            }

                            @Override
                            public void onNext(GetVideoModel getVideoModel) {
                                if (getView().getPageIndex() == 1) {//初始化 pageIndex = 1
                                    if (getVideoModel.isSuccess()) {
                                        if (getVideoModel.isEmptyData())
                                            getView().onNetEmptyNotifyUI();
                                        else {
                                            getView().setTotalCount(getVideoModel.getDataCount());
                                            getView().onGetVideoCompleted(getVideoModel.getData());
                                        }
                                    } else {
                                        showToast(getVideoModel.getErrMsg());
                                    }
                                }
                            }
                        })
        );
    }

    @Override
    public void getMoreVideo() {
        int totalCount = getView().getTotalCount();
        int pageIndex = getView().getPageIndex();
        if (totalCount <= 2) {
            Log.e("info", "getMoreVideo: " +totalCount+ "  "+pageIndex);
            getView().onGetMoreVideoCompleted(null);
            return ;
        }

        if (getView().getmList().size() < totalCount) {
            getView().setPageIndex(++pageIndex);
            showLoadingView(R.string.loading_more_text);
            getMore();
        } else {
            //no more
            getView().onGetMoreVideoCompleted(null);
            showToast(R.string.no_more_data_text);
        }

    }

    private void getMore() {
        addSubscription(
                RequestHelper.getInstance().getVideo(getView().getType(),getView().getPageIndex()).
                        subscribe(new Subscriber<GetVideoModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                int recoveryPageIndex = getView().getPageIndex() - 1;
                                getView().setPageIndex(recoveryPageIndex);
                                getView().onGetMoreVideoCompleted(null);
                                dismissLoadingView();
                                showToast(R.string.net_error_toast);
                            }

                            @Override
                            public void onNext(GetVideoModel getVideoModel) {
                                dismissLoadingView();
                                if (getVideoModel.isSuccess()) {
                                    if (getVideoModel.isEmptyData())
                                        getView().onGetMoreVideoCompleted(null);
                                    else {
                                        getView().onGetMoreVideoCompleted(getVideoModel.getData());
                                    }
                                } else {
                                    getView().onGetMoreVideoCompleted(null);
                                    showToast(getVideoModel.getErrMsg());
                                }
                            }
                        })
        );
    }


}
