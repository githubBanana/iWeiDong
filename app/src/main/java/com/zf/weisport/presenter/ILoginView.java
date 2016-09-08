package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.LoginModel;

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
     * 第三方登录回调
     */
    void onOpenLoginCompleted(LoginModel loginModel);

    /**
     * 第三方同步资料完成回调
     */
    void onSyncInfoCompleted();

    /**
     * 获取用户名
     * @return
     */
    String getLoginName();

    /**
     * 设置用户名
     * @param userName
     */
    void setLoginName(String userName);

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

    /**
     * 设置微信登录
     * @param enable
     */
    void setWecha(boolean enable);

    /**
     * 设置微博登录
     * @param enable
     */
    void setWeibo(boolean enable);

    /**
     * 设置QQ登录
     * @param enable
     */
    void setQq(boolean enable);

}
