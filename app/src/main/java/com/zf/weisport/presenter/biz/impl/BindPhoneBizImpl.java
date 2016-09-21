package com.zf.weisport.presenter.biz.impl;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.SMSCodeModel;
import com.zf.weisport.presenter.IBindPhoneView;
import com.zf.weisport.presenter.biz.IBindPhoneBiz;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 10:57
 * @email Xs.lin@foxmail.com
 */
public class BindPhoneBizImpl extends BaseBiz<IBindPhoneView> implements IBindPhoneBiz {

    public BindPhoneBizImpl(IBindPhoneView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void commit() {
        String phone = getView().getPhone();
        String smsCode = getView().getSMSCode();
        String password = getView().getPassword();
        if (TextUtils.isEmpty(phone)) {
            showToast(R.string.et_hint_username);
            return;
        }
        if (TextUtils.isEmpty(smsCode)) {
            showToast(R.string.et_hint_verify);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast(R.string.et_hint_password);
            return;
        }
        addSubscription(
                RequestHelper.getInstance().requestBindAccount(User.getUser().getId(),phone,password).
                        doOnNext(baseModel -> {
                            if (baseModel.isSuccess()) {
                                User.getUser().setPhone(phone);
                                AppDatabaseCache.getCache(UIUtil.getContext()).updateUserPhone(phone);
                                getView().onCommitCompleted();
                            }
                            else
                                showToast(baseModel.getErrMsg());
                        }).subscribe(getSubscriber())
        );
    }

    @Override
    public void getSMSCode() {

        int TYPE = 3;//类型:第三方绑定
        addSubscription(
                RequestHelper.getInstance().getSMSCode(getView().getPhone(),TYPE).
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
                                    getView().setSMSCode(smsCodeModel.getData().get(0).getCode());
                                    getView().notifyUIChange();
                                } else {
                                    getView().onGetVerifyCodesStaus(false);
                                    showToast(smsCodeModel.getErrMsg());
                                }
                            }
                        })
        );

    }
}
