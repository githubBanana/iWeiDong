package com.zf.iweidong.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.zf.iweidong.R;
import com.zf.iweidong.databinding.FragmentForgetPsdBinding;
import com.zf.iweidong.listener.OnChangeTilte;
import com.zf.iweidong.manager.util.UIUtil;
import com.zf.iweidong.ui.callback.IForgetPsdCallback;
import com.zf.iweidong.ui.fragment.base.BaseFragment;
import com.zf.iweidong.ui.viewmodel.ForgetPsdViewModel;

import java.util.Locale;

/**
 * @version V1.0 <找回密码界面>
 * @author: Xs
 * @date: 2016-09-05 15:34
 * @email Xs.lin@foxmail.com
 */
public class ForgetPsdFragment extends BaseFragment<ForgetPsdViewModel,FragmentForgetPsdBinding> implements IForgetPsdCallback{
    private static final String TAG = "ForgetPsdFragment";

    private int total = 60;

    public ForgetPsdFragment(){

    }

    OnChangeTilte mOnChangeTitle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnChangeTitle = (OnChangeTilte) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implements OnChangeTitle");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forget_psd;
    }

    @Override
    protected ForgetPsdViewModel initViewModel() {
        return new ForgetPsdViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setForgetPsdViewModel(getViewModel());
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnChangeTitle.onChangeCenterTitle(R.string.forget_psd_title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!(getActivity() instanceof View.OnClickListener)) {
            throw new IllegalStateException("activity not implement View.OnClickListener");
        }

        getBinding().btnNext.setOnClickListener((View.OnClickListener) getActivity());
        getBinding().btnVerify.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(getViewModel().getMobileNumber())) {
                showToast(R.string.et_hint_username);
                return;
            }
            getViewModel().getSMSCode();
            exector();
        });

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
        getBinding().btnVerify.setText(UIUtil.getContext().getString(R.string.btn_get_verify_text));
    }

    @Override
    public void onGetVerifyCodesStaus(boolean isSuccess) {
        if (!isSuccess) restore();
    }
}
