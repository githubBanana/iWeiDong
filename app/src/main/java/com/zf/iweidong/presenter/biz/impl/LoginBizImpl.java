package com.zf.iweidong.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.xs.net.retrofit.RequestHelper;
import com.zf.iweidong.presenter.ILoginView;
import com.zf.iweidong.presenter.biz.ILoginBiz;
import com.zf.utils.PhoneUtil;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 17:13
 * @email Xs.lin@foxmail.com
 */
public class LoginBizImpl extends BaseBiz<ILoginView> implements ILoginBiz {
    private static final String TAG = "LoginBizImpl";

    public LoginBizImpl(ILoginView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void login() {
        showLoadingView();
        RequestHelper.getInstance().login(getView().getUserName(),
                getView().getPassword(), PhoneUtil.getUUID(getCallBack()),1);

    }

    @Override
    public void loadInitData() {
        getView().setUserName("hello");
        getView().setPassword("1234");
    }


}
