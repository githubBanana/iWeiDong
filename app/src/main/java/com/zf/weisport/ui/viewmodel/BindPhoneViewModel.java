package com.zf.weisport.ui.viewmodel;

import android.databinding.ObservableField;

import com.xs.basic_mvvm.model.BaseModel;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.presenter.IBindPhoneView;
import com.zf.weisport.presenter.biz.IBindPhoneBiz;
import com.zf.weisport.presenter.biz.impl.BindPhoneBizImpl;
import com.zf.weisport.ui.callback.IBindPhoneCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 10:46
 * @email Xs.lin@foxmail.com
 */
public class BindPhoneViewModel extends BaseViewModel<IBindPhoneCallback,BaseModel> implements IBindPhoneView{

    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> smsCode = new ObservableField<>("");
    public ObservableField<String> password =new ObservableField<>("");

    private IBindPhoneBiz biz;
    public BindPhoneViewModel(IBindPhoneCallback iBindPhoneCallback) {
        super(iBindPhoneCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new BindPhoneBizImpl(this,this);
    }

    /**
     * 提交
     */
    public void commit() {
        biz.commit();
    }

    /**
     * 获取验证码
     */
    public void getCode() {
        biz.getSMSCode();
    }
    @Override
    public void onCommitCompleted() {
        getCallback().onCommitSuccess();
    }

    @Override
    public void onGetVerifyCodesStaus(boolean isSuccess) {
        getCallback().onGetVerifyCodesStaus(isSuccess);
    }

    @Override
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Override
    public String getPhone() {
        return this.phone.get();
    }

    @Override
    public void setSMSCode(String smsCode) {
        this.smsCode.set(smsCode);
    }

    @Override
    public String getSMSCode() {
        return this.smsCode.get();
    }

    @Override
    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String getPassword() {
        return password.get();
    }
}
