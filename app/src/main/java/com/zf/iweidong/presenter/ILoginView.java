package com.zf.iweidong.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.iweidong.model.LoginModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 11:25
 * @email Xs.lin@foxmail.com
 */
public interface ILoginView extends IBaseView {

    /**
     * 登录回调
     * @param loginModel
     */
    void onLoginCompleted(LoginModel loginModel);

    /**
     * 获取用户名
     * @return
     */
    String getUserName();

    /**
     * 设置用户名
     * @param userName
     */
    void setUserName(String userName);

    /**
     * 获取密码
     * @return
     */
    String getPassword();

    /**
     * 设置密码
     * @param pssword
     */
    void setPassword(String pssword);
}
