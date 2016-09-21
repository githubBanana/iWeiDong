package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 09:25
 * @email Xs.lin@foxmail.com
 */
public interface IModifyPwdView extends IBaseView {

    /**
     * 提交密码完成
     */
    void onCommitCompleted();

    /**
     * 设置旧密码
     * @param oldPwd
     */
    void setOldPwd(String oldPwd);

    /**
     * 获取旧密码
     * @return
     */
    String getOldPwd();

    /**
     * 设置新密码
     * @param newPwd
     */
    void setNewPwd(String newPwd);

    /**
     * 获取新密码
     * @return
     */
    String getNewPwd();

    /**
     * 设置重复新密码
     * @param repeatPwd
     */
    void setRepeatPwd(String repeatPwd);

    /**
     * 获取重复新密码
     * @return
     */
    String getRepeatPwd();
}
