package com.zf.iweidong.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-07 10:57
 * @email Xs.lin@foxmail.com
 */
public interface IForgetPsdBiz extends IBaseBiz{
    /**
     * 获取验证码
     */
    void getVerifyCodes();
}
