package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-06 16:42
 * @email Xs.lin@foxmail.com
 */
public interface IRegisterBiz extends IBaseBiz {

    /**
     * 注册
     */
    void register();

    /**
     * 获取验证码
     */
    void getVerifyCodes();
}
