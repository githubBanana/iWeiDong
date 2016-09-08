package com.zf.weisport.ui.callback;

import com.zf.weisport.model.LoginModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 11:23
 * @email Xs.lin@foxmail.com
 */
public interface ILoginCallback extends IBaseCallback {

    /**
     * 登录成功回调
     */
    void onLoginSuccess(LoginModel loginModel);

    /**
     * 第三方登录成功回调
     */
    void onOpenLoginSuccess();


}
