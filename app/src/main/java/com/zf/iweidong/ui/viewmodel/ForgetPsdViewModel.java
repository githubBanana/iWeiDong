package com.zf.iweidong.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.iweidong.model.ForgetPsdModel;
import com.zf.iweidong.presenter.IForgetPsdView;
import com.zf.iweidong.presenter.biz.IForgetPsdBiz;
import com.zf.iweidong.presenter.biz.impl.ForgetPsdBizImpl;
import com.zf.iweidong.ui.callback.IForgetPsdCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-07 10:52
 * @email Xs.lin@foxmail.com
 */
public class ForgetPsdViewModel extends BaseViewModel<IForgetPsdCallback,ForgetPsdModel> implements IForgetPsdView {

    public String mobileNumber;
    public String codes;
    public String codesCountDown;

    private IForgetPsdBiz biz;

    public ForgetPsdViewModel(IForgetPsdCallback iForgetPsdCallback) {
        super(iForgetPsdCallback);
        biz = getBiz();
        biz.loadInitData();
    }

    /**
     * 获取验证码
     */
    public void getSMSCode() {
        biz.getVerifyCodes();
    }

    @Override
    protected BaseBiz createBiz() {
        return new ForgetPsdBizImpl(this,this);
    }

    @Override
    public String getMobileNumber() {
        return this.mobileNumber;
    }

    @Override
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String getCodes() {
        return this.codes;
    }

    @Override
    public void setCodes(String codes) {
        this.codes = codes;
    }

    @Override
    public String getCodesCountDown() {
        return this.codesCountDown;
    }

    @Override
    public void setCodesCountDown(String codesCountDown) {
        this.codesCountDown = codesCountDown;
    }

    @Override
    public void onGetVerifyCodesStaus(boolean isSuccess) {
        getCallback().onGetVerifyCodesStaus(isSuccess);
    }

}
