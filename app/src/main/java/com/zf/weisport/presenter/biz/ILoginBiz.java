package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 17:05
 * @email Xs.lin@foxmail.com
 */
public interface ILoginBiz extends IBaseBiz{

    /**
     * 登录
     */
    void login();

    /**
     * 第三方登录
     */
    void thirdPartyLogin(String platFormName);

    /**
     * 根据第三方平台登录信息向服务器请求登录
     */
    void openLogin();

    /**
     * 第三方同步资料
     */
    void SyncInfo();
}
