package com.zf.weisport.presenter.biz.impl;

import android.text.TextUtils;
import android.util.Log;

import com.diy.diylibrary.sharesdk.AuthorizeLogin;
import com.diy.diylibrary.sharesdk.ThirdPartyUser;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.xs.net.retrofit.DESUtil;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.PhoneUtil;
import com.zf.weisport.manager.util.SPUtil;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.presenter.ILoginView;
import com.zf.weisport.presenter.biz.ILoginBiz;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
                        getView().onLoginCompleted(loginModel.getData().get(0));
                    else
                        showToast(loginModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }

    @Override
    public void thirdPartyLogin(String platFormName) {
        Log.e(TAG, "thirdPartyLogin: "+platFormName );
        AuthorizeLogin.getPresenter(UIUtil.getContext()).showUser(this, platFormName);
        // unable touch
        if (QQ.NAME.equals(platFormName))
            getView().setQq(false);
        else if (Wechat.NAME.equals(platFormName))
            getView().setWecha(false);
        else if (SinaWeibo.NAME.equals(platFormName))
            getView().setWeibo(false);
        else {}
        getView().notifyUIChange();
    }

    @Override
    public void openLogin() {
        showLoadingView();
        String openId = ThirdPartyUser.getInstance().getOpenId();
        int    type   = ThirdPartyUser.getInstance().getType();
        addSubscription(
            RequestHelper.getInstance().openLogin(openId,type,PhoneUtil.getUUID(UIUtil.getContext()),1).
                doOnNext(loginModel -> {
                    if (loginModel.isSuccess())
                        getView().onOpenLoginCompleted(loginModel.getData().get(0));
                    else
                        showToast(loginModel.getErrMsg());
                }).subscribe(getSubscriber())
        );
    }

    @Override
    public void SyncInfo() {
        ThirdPartyUser thirdPartyUser = ThirdPartyUser.getInstance();
        addSubscription(
            RequestHelper.getInstance().SyncInfo(User.getUser().getId(),thirdPartyUser.getUserIconUrl(),
                thirdPartyUser.getUserGender(),thirdPartyUser.getNickname()).
                doOnNext(syncInfoModel -> {
                    if (syncInfoModel.isSuccess())
                        getView().onSyncInfoCompleted();
                }).subscribe(getSubscriber())
        );
    }

    @Override
    public void loadInitData() {
        /**
         * 初始化登录界面
         */
        final String userNameEncry = SPUtil.readNormalData(UIUtil.getContext(),"username");
        final String passWordEncry = SPUtil.readNormalData(UIUtil.getContext(),"password");
        if (!TextUtils.isEmpty(userNameEncry))
            getView().setLoginName(DESUtil.decryptDoNet(userNameEncry));
        if (!TextUtils.isEmpty(passWordEncry))
            getView().setPassword(DESUtil.decryptDoNet(passWordEncry));
        enableThirdPartyLoginTouch();
    }

    /**
     * enable qq weibo wecha touch
     */
    private void enableThirdPartyLoginTouch() {
        getView().setQq(true);
        getView().setWecha(true);
        getView().setWeibo(true);
        getView().notifyUIChange();
    }
    private static final String TAG = LoginBizImpl.class.getSimpleName();

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        enableThirdPartyLoginTouch();
        //初始化第三方账号登录信息
        ThirdPartyUser.getInstance().initPlatformUserInfo(platform);
        //根据第三方登录信息向服务器请求登录
        openLogin();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        enableThirdPartyLoginTouch();
        if (platform.getName().equals(Wechat.NAME))
            showToast(UIUtil.getContext().getString(R.string.ssdk_wechat_client_inavailable));
    }

    @Override
    public void onCancel(Platform platform, int i) {
        enableThirdPartyLoginTouch();
    }
}
