package com.zf.weisport.presenter.biz.impl;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.presenter.IModifyPwdView;
import com.zf.weisport.presenter.biz.IModifyPwdBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 09:27
 * @email Xs.lin@foxmail.com
 */
public class ModifyPwdBizImpl extends BaseBiz<IModifyPwdView> implements IModifyPwdBiz {

    public ModifyPwdBizImpl(IModifyPwdView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void commit() {
        String oldPwd = getView().getOldPwd();
        String newPwd = getView().getNewPwd();
        String repeatPwd = getView().getRepeatPwd();
        if (TextUtils.isEmpty(oldPwd)) {
            showToast(R.string.et_hint_password_old);
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            showToast(R.string.et_hint_password_new);
            return;
        }
        if (TextUtils.isEmpty(repeatPwd)) {
            showToast(R.string.repeat_et_hint_password_new);
            return;
        }
        if (!repeatPwd.equals(newPwd)) {
            showToast(R.string.repeat_password_error);
            return;
        }
        showLoadingView();
        addSubscription(
                RequestHelper.getInstance().updPassword(User.getUser().getPhone(),repeatPwd,oldPwd).
                        doOnNext(baseModel -> {
                            if (baseModel.isSuccess())
                                getView().onCommitCompleted();
                            else
                                showToast(baseModel.getErrMsg());
                        }).subscribe(getSubscriber())
        );

    }

}
