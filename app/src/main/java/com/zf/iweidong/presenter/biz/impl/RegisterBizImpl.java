package com.zf.iweidong.presenter.biz.impl;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.iweidong.R;
import com.zf.iweidong.manager.net.RequestHelper;
import com.zf.iweidong.manager.util.UIUtil;
import com.zf.iweidong.model.SMSCodeModel;
import com.zf.iweidong.presenter.IRegisterView;
import com.zf.iweidong.presenter.biz.IRegisterBiz;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-06 16:44
 * @email Xs.lin@foxmail.com
 */
public class RegisterBizImpl extends BaseBiz<IRegisterView> implements IRegisterBiz {
    private static final String TAG = RegisterBizImpl.class.getSimpleName();
    public RegisterBizImpl(IRegisterView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void register() {

        if (TextUtils.isEmpty(getView().getMobileNumber())) {
            showToast(R.string.et_hint_username);
            return;
        }
        if (TextUtils.isEmpty(getView().getPassword())) {
            showToast(R.string.et_hint_password);
            return;
        }

        showLoadingView();
        addSubscription(
            RequestHelper.getInstance().register(getView().getMobileNumber(),getView().getPassword()).
                doOnNext(registerModel -> {
                    if (registerModel.isSuccess())
                        getView().onRegisterCompleted(registerModel);
                    else
                        showToast(registerModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }

    @Override
    public void getVerifyCodes() {

        int TYPE = 1;//类型:注册
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
