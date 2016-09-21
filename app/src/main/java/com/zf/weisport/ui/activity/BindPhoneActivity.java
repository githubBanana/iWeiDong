package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zf.weisport.R;
import com.zf.weisport.databinding.ActivityBindPhoneBinding;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.IBindPhoneCallback;
import com.zf.weisport.ui.viewmodel.BindPhoneViewModel;

import java.util.Locale;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 10:11
 * @email Xs.lin@foxmail.com
 */
public class BindPhoneActivity extends BaseActivity<BindPhoneViewModel,ActivityBindPhoneBinding>
implements IBindPhoneCallback{

    private int total = 60;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,BindPhoneActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected BindPhoneViewModel initViewModel() {
        return new BindPhoneViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setBindPhoneViewModel(getViewModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone,true);

        getBinding().bindBtn.setOnClickListener(view -> getViewModel().commit());
        getBinding().btnVerify.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(getViewModel().getPhone())) {
                showToast(R.string.et_hint_username);
                return;
            }
            getViewModel().getCode();
            exector();
        });
        getBinding().btnVerify.setText(R.string.btn_get_verify_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.bind_account);
    }

    @Override
    public void onCommitSuccess() {
        finish();
    }

    @Override
    public void onGetVerifyCodesStaus(boolean isSuccess) {
        if (!isSuccess) restore();
    }

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

}
