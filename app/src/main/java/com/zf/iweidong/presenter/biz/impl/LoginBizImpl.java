package com.zf.iweidong.presenter.biz.impl;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.xs.net.retrofit.DESUtil;
import com.zf.iweidong.manager.net.RequestHelper;
import com.zf.iweidong.manager.util.PhoneUtil;
import com.zf.iweidong.manager.util.SPUtil;
import com.zf.iweidong.manager.util.UIUtil;
import com.zf.iweidong.presenter.ILoginView;
import com.zf.iweidong.presenter.biz.ILoginBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 17:13
 * @email Xs.lin@foxmail.com
 */
public class LoginBizImpl extends BaseBiz<ILoginView> implements ILoginBiz {

    public LoginBizImpl(ILoginView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void login() {
        showLoadingView();
        addSubscription(
        RequestHelper.getInstance().login(getView().getLoginName(),
                getView().getPassword(), PhoneUtil.getUUID(UIUtil.getContext()),1).
                doOnNext(loginModel -> {
                    if (loginModel.isSuccess())
                        getView().onLoginCompleted(loginModel);
                    else
                        showToast(loginModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }

    @Override
    public void loadInitData() {
        final String userNameEncry = SPUtil.readNormalData(UIUtil.getContext(),"username");
        final String passWordEncry = SPUtil.readNormalData(UIUtil.getContext(),"password");
        if (!TextUtils.isEmpty(userNameEncry))
            getView().setLoginName(DESUtil.decryptDoNet(userNameEncry));
        if (!TextUtils.isEmpty(passWordEncry))
            getView().setPassword(DESUtil.decryptDoNet(passWordEncry));
    }


}
