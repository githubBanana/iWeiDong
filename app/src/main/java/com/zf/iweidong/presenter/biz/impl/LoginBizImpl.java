package com.zf.iweidong.presenter.biz.impl;

import android.text.TextUtils;

import com.diy.diylibrary.sharesdk.AuthorizeLogin;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.xs.net.retrofit.DESUtil;
import com.zf.iweidong.R;
import com.zf.iweidong.manager.net.RequestHelper;
import com.zf.iweidong.manager.util.PhoneUtil;
import com.zf.iweidong.manager.util.SPUtil;
import com.zf.iweidong.manager.util.UIUtil;
import com.zf.iweidong.presenter.ILoginView;
import com.zf.iweidong.presenter.biz.ILoginBiz;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * @version V1.0 <登录业务层>
 * @author: Xs
 * @date: 2016-09-05 17:13
 * @email Xs.lin@foxmail.com
 */
public class LoginBizImpl extends BaseBiz<ILoginView>
        implements ILoginBiz,PlatformActionListener {

    public LoginBizImpl(ILoginView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(getView().getLoginName())) {
            showToast(R.string.et_hint_username);
            return;
        }
        if (TextUtils.isEmpty(getView().getPassword())) {
            showToast(R.string.et_hint_password);
            return;
        }

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
    public void thirdPartyLogin(String platFormName) {
        AuthorizeLogin.getPresenter(UIUtil.getContext()).showUser(this, platFormName);
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


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
