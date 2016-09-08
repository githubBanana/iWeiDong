package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.SMSCodeModel;
import com.zf.weisport.presenter.IForgetPsdView;
import com.zf.weisport.presenter.biz.IForgetPsdBiz;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-07 11:03
 * @email Xs.lin@foxmail.com
 */
public class ForgetPsdBizImpl extends BaseBiz<IForgetPsdView> implements IForgetPsdBiz{

    public ForgetPsdBizImpl(IForgetPsdView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void getVerifyCodes() {

        int TYPE = 2;//类型:找回密码
        addSubscription(
                RequestHelper.getInstance().getSMSCode(getView().getMobileNumber(),TYPE).
                        subscribe(new Subscriber<SMSCodeModel>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                showToast(com.xs.basic_mvvm.R.string.net_error_toast);
                                getView().onGetVerifyCodesStaus(false);
                            }

                            @Override
                            public void onNext(SMSCodeModel smsCodeModel) {
                                if (smsCodeModel.isSuccess()) {
                                    getView().onGetVerifyCodesStaus(true);
                                    getView().setCodes(smsCodeModel.getData().get(0).getCode());
                                    getView().notifyUIChange();
                                } else {
                                    getView().onGetVerifyCodesStaus(false);
                                    showToast(smsCodeModel.getErrMsg());
                                }
                            }
                        })
        );
    }

    @Override
    public void loadInitData() {
        getView().setCodesCountDown(UIUtil.getContext().getString(R.string.btn_get_verify_text));
    }
}
