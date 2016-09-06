package com.zf.iweidong.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zf.iweidong.R;
import com.zf.iweidong.databinding.FragmentLoginBinding;
import com.zf.iweidong.listener.OnChangeTilte;
import com.zf.iweidong.model.LoginModel;
import com.zf.iweidong.ui.callback.ILoginCallback;
import com.zf.iweidong.ui.fragment.base.BaseFragment;
import com.zf.iweidong.ui.viewmodel.LoginViewModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 10:45
 * @email Xs.lin@foxmail.com
 */
public class LoginFragment extends BaseFragment<LoginViewModel,FragmentLoginBinding> implements ILoginCallback {
    private static final String TAG = "LoginFragment";

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
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnChangeTitle.onChangeCenterTitle(R.string.login_title);
    }

    @Override
    public void onLoginSuccess(LoginModel loginModel) {
    }
}
