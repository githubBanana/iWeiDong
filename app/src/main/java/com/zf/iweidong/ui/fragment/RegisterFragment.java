package com.zf.iweidong.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.zf.iweidong.R;
import com.zf.iweidong.databinding.FragmentRegisterBinding;
import com.zf.iweidong.listener.OnChangeTilte;
import com.zf.iweidong.manager.util.UIUtil;
import com.zf.iweidong.model.RegisterModel;
import com.zf.iweidong.ui.callback.IRegisterCallback;
import com.zf.iweidong.ui.fragment.base.BaseFragment;
import com.zf.iweidong.ui.viewmodel.RegisterViewModel;

import java.util.Locale;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 13:55
 * @email Xs.lin@foxmail.com
 */
public class RegisterFragment extends BaseFragment<RegisterViewModel,FragmentRegisterBinding>
        implements IRegisterCallback {

    private int total = 60;

    public RegisterFragment() {
        // Required empty public constructor
    }

    OnChangeTilte mOnChangeTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    protected RegisterViewModel initViewModel() {
        return new RegisterViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setRegisterViewModel(getViewModel());
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
        /**
         * 监听 提交注册信息 获取验证码
         */
        getBinding().btnRegister.setOnClickListener(view1 -> getViewModel().register());
        getBinding().btnVerify.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(getViewModel().getMobileNumber())) {
                showToast(R.string.et_hint_username);
                return;
            }
            getViewModel().getVerifyCodes();
            exector();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnChangeTitle.onChangeCenterTitle(R.string.register_title);
    }

    @Override
    public void onRegisterSuccess(RegisterModel registerModel) {
        showToast(UIUtil.getContext().getString(R.string.register_success_text));
        getActivity().onBackPressed();
    }

    @Override
    public void onGetVerifyCodesStaus(boolean isSuccess) {
        if (!isSuccess) restore();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            total = total - 1;
            String text = String.format(Locale.CHINA, "%ds", total);
            if (total == 0) {
                getBinding().btnVerify.removeCallbacks(this);
                restore();
                return;
            }
            getBinding().btnVerify.setText(text);
            getBinding().btnVerify.postDelayed(this,1000 * 1);
        }
    };

    /**
     * 开始验证码倒计时
     */
    private void exector() {
        getBinding().btnVerify.setEnabled(false);
        getBinding().btnVerify.post(runnable);
    }

    /**
     * 恢复验证码，可重新获取验证码
     */
    private void restore() {
        total = 60;
        getBinding().btnVerify.removeCallbacks(runnable);
        getBinding().btnVerify.setEnabled(true);
        getBinding().btnVerify.setText(getActivity().getString(R.string.btn_get_verify_text));
    }
}
