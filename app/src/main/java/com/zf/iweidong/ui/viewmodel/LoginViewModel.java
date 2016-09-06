package com.zf.iweidong.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.net.retrofit.DESUtil;
import com.zf.iweidong.manager.util.SPUtil;
import com.zf.iweidong.manager.util.UIUtil;
import com.zf.iweidong.model.LoginModel;
import com.zf.iweidong.presenter.ILoginView;
import com.zf.iweidong.presenter.biz.ILoginBiz;
import com.zf.iweidong.presenter.biz.impl.LoginBizImpl;
import com.zf.iweidong.ui.callback.ILoginCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 11:21
 * @email Xs.lin@foxmail.com
 */
public class LoginViewModel extends BaseViewModel<ILoginCallback,LoginModel> implements ILoginView {

    public String loginName;
    public String password;
    public ILoginBiz biz;
    public LoginViewModel(ILoginCallback iLoginCallback) {
        super(iLoginCallback);
        biz = getBiz();
        biz.loadInitData();
    }

    public void login() {
        biz.login();
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
    public void onLoginCompleted(LoginModel loginModel) {
        /**
         * 保存用户登录信息
         */
        final String userNameEncry = DESUtil.encryptAsDoNet(getLoginName());
        final String passWordEncry = DESUtil.encryptAsDoNet(getPassword());
        SPUtil.saveNormalData(UIUtil.getContext(),"username",userNameEncry);
        SPUtil.saveNormalData(UIUtil.getContext(),"password",passWordEncry);
        getCallback().onLoginSuccess(loginModel);
    }


}
