package com.zf.weisport.presenter.biz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 10:56
 * @email Xs.lin@foxmail.com
 */
public interface IBindPhoneBiz {

    /**
     * 提交
     */
    void commit();

    /**
     * 获取验证码
     */
    void getSMSCode();
}
