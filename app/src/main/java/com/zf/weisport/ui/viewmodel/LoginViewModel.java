package com.zf.weisport.ui.viewmodel;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.net.retrofit.DESUtil;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.util.SPUtil;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.manager.util.UserUtil;
import com.zf.weisport.model.LoginModel;
import com.zf.weisport.presenter.ILoginView;
import com.zf.weisport.presenter.biz.ILoginBiz;
import com.zf.weisport.presenter.biz.impl.LoginBizImpl;
import com.zf.weisport.ui.callback.ILoginCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 11:21
 * @email Xs.lin@foxmail.com
 */
public class LoginViewModel extends BaseViewModel<ILoginCallback,LoginModel> implements ILoginView {

    public String       loginName;
    public String       password;
    public boolean      wecha;
    public boolean      weibo;
    public boolean      qq;
    public ILoginBiz biz;
    public LoginViewModel(ILoginCallback iLoginCallback) {
        super(iLoginCallback);
        biz = getBiz();
        biz.loadInitData();
    }

    public void login() {
        biz.login();
    }
    public void thirdPartyLogin(String platFormName) {
        biz.thirdPartyLogin(platFormName);
    }

    @Override
    protected BaseBiz createBiz() {
        return new LoginBizImpl(this,this);
    }

    @Override
    public String getLoginName() {
        return this.loginName;
    }

    @Override
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setWecha(boolean enable) {
        this.wecha = enable;
    }

    @Override
    public void setWeibo(boolean enable) {
        this.weibo = enable;
    }

    @Override
    public void setQq(boolean enable) {
        this.qq = enable;
    }

    @Override
    public void onLoginCompleted(LoginModel loginModel) {
        /**
         * 保存用户登录信息到缓存、数据库
         */
        UserUtil.setLogin();
        UserUtil.initUser(loginModel);
        AppDatabaseCache.getCache(UIUtil.getContext()).userAdd(User.getUser());
        /**
         * 临时保存用户登录信息
         */
        final String userNameEncry = DESUtil.encryptAsDoNet(getLoginName());
        final String passWordEncry = DESUtil.encryptAsDoNet(getPassword());
        SPUtil.saveNormalData(UIUtil.getContext(),"username",userNameEncry);
        SPUtil.saveNormalData(UIUtil.getContext(),"password",passWordEncry);

        getCallback().onLoginSuccess(loginModel);
    }

    @Override
    public void onOpenLoginCompleted(LoginModel loginModel) {
        /**
         * 保存用户登录信息到缓存、数据库
         */
        UserUtil.setLoginThirdparty();
        UserUtil.initUser(loginModel);
        AppDatabaseCache.getCache(UIUtil.getContext()).userAdd(User.getUser());
        //第三方同步资料请求
        if (TextUtils.isEmpty(loginModel.IS_Sync) || "0".equals(loginModel.IS_Sync))
            biz.SyncInfo();
        else
            getCallback().onOpenLoginSuccess();

    }

    @Override
    public void onSyncInfoCompleted() {
        getCallback().onOpenLoginSuccess();
    }

}
