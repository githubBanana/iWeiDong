package com.zf.iweidong.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
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

    public String userName;
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
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
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

    }

}
