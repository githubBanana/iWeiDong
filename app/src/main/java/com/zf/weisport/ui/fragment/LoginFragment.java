package com.zf.weisport.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zf.weisport.R;
import com.zf.weisport.databinding.FragmentLoginBinding;
import com.zf.weisport.listener.OnChangeTilte;
import com.zf.weisport.model.LoginModel;
import com.zf.weisport.ui.callback.ILoginCallback;
import com.zf.weisport.ui.fragment.base.BaseFragment;
import com.zf.weisport.ui.viewmodel.LoginViewModel;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @version V1.0 <登录界面>
 * @author: Xs
 * @date: 2016-09-05 10:45
 * @email Xs.lin@foxmail.com
 */
public class LoginFragment extends BaseFragment<LoginViewModel,FragmentLoginBinding>
        implements ILoginCallback {

    OnChangeTilte mOnChangeTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    protected LoginViewModel initViewModel() {
        return new LoginViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setLoginViewModel(getViewModel());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnChangeTitle = (OnChangeTilte) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement OnChangeTitle");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (! (getActivity() instanceof View.OnClickListener)) {
            throw new IllegalStateException("activity not implement View.OnClickListener");
        }
        getBinding().btnForgetPassword.setOnClickListener((View.OnClickListener) getActivity());
        getBinding().btnNothaveAccount.setOnClickListener((View.OnClickListener) getActivity());
        getBinding().btnSubmit.setOnClickListener(view1 -> getViewModel().login());
        getBinding().tvWeibo.setOnClickListener(view1 -> getViewModel().thirdPartyLogin(SinaWeibo.NAME));

        getBinding().tvWeixin.setOnClickListener(view1 -> getViewModel().thirdPartyLogin(Wechat.NAME));
        getBinding().tvQq.setOnClickListener(view1 -> getViewModel().thirdPartyLogin(QQ.NAME));

    }

    @Override
    public void onResume() {
        super.onResume();
        mOnChangeTitle.onChangeCenterTitle(R.string.login_title);
    }

    @Override
    public void onLoginSuccess(LoginModel loginModel) {
        getActivity().onBackPressed();
    }

    @Override
    public void onOpenLoginSuccess() {
        getActivity().onBackPressed();
    }


}
