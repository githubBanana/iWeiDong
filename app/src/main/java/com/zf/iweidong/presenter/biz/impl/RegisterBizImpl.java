package com.zf.iweidong.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.iweidong.R;
import com.zf.iweidong.manager.util.UIUtil;
import com.zf.iweidong.presenter.IRegisterView;
import com.zf.iweidong.presenter.biz.IRegisterBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-06 16:44
 * @email Xs.lin@foxmail.com
 */
public class RegisterBizImpl extends BaseBiz<IRegisterView> implements IRegisterBiz {

    public RegisterBizImpl(IRegisterView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void register() {
    }

    @Override
    public void getVerifyCodes() {
    }

    @Override
    public void loadInitData() {
        getView().setCodesCountDown(UIUtil.getContext().getString(R.string.btn_get_verify_text));
    }
}
