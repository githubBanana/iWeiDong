package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
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

}
