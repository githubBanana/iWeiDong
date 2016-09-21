package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.model.BaseModel;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.presenter.IModifyPwdView;
import com.zf.weisport.presenter.biz.IModifyPwdBiz;
import com.zf.weisport.presenter.biz.impl.ModifyPwdBizImpl;
import com.zf.weisport.ui.callback.IModifyPwdCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 09:22
 * @email Xs.lin@foxmail.com
 */
public class ModifyPwdViewModel extends BaseViewModel<IModifyPwdCallback,BaseModel> implements IModifyPwdView {

    public String oldPwd;
    public String newPwd;
    public String repeatPwd;

    private IModifyPwdBiz biz;
    public ModifyPwdViewModel(IModifyPwdCallback iModifyPwdCallback) {
        super(iModifyPwdCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new ModifyPwdBizImpl(this,this);
    }

    /**
     * 提交密码
     */
    public void commit() {
        biz.commit();
    }

    @Override
    public void onCommitCompleted() {
        getCallback().onCommitSuccess();
    }

    @Override
    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    @Override
    public String getOldPwd() {
        return this.oldPwd;
    }

    @Override
    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    @Override
    public String getNewPwd() {
        return this.newPwd;
    }

    @Override
    public void setRepeatPwd(String repeatPwd) {
        this.repeatPwd = repeatPwd;
    }

    @Override
    public String getRepeatPwd() {
        return this.repeatPwd;
    }
}
