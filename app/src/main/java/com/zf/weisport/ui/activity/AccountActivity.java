package com.zf.weisport.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zf.weisport.R;
import com.zf.weisport.listener.OnChangeTilte;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.fragment.ForgetPsdFragment;
import com.zf.weisport.ui.fragment.ForgetPsdNextFragment;
import com.zf.weisport.ui.fragment.LoginFragment;
import com.zf.weisport.ui.fragment.RegisterFragment;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-02 16:50
 * @email Xs.lin@foxmail.com
 */
public class AccountActivity extends BaseActivity implements View.OnClickListener,OnChangeTilte{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account,true);
        commitFragment(Type.LOGIN,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.login_title);
    }

    public void commitFragment(Type type, boolean addBackStack) {
        Fragment fragment = null;
        switch (type) {
            case LOGIN:
                fragment = new LoginFragment();
                break;
            case REGISTER:
                fragment = new RegisterFragment();
                break;
            case FORGET_PASSWORD_STEP_ONE:
                fragment = new ForgetPsdFragment();
                break;
            case FORGET_PASSWORD_STEP_TWO:
                fragment = new ForgetPsdNextFragment();
                break;
        }

        int enter = android.support.design.R.anim.abc_fade_in;
        int exit = android.support.design.R.anim.abc_fade_out;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(enter,exit);
        if (addBackStack)
            transaction.replace(R.id.fl_container,fragment).addToBackStack(null).commit();
        else
            transaction.replace(R.id.fl_container,fragment).commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                commitFragment(Type.FORGET_PASSWORD_STEP_TWO,true);
            case R.id.btn_forget_password:
                commitFragment(Type.FORGET_PASSWORD_STEP_ONE,true);
                break;
            case R.id.btn_nothave_account:
                commitFragment(Type.REGISTER,true);
                break;
        }
    }

    @Override
    public void onChangeCenterTitle(int titleId) {
        setCenterTitle(titleId);
    }

    @Override
    public void onChangeTitle(int titleId) {
        setTitle(titleId);
    }

    public enum Type {
        LOGIN,//登录
        REGISTER,//注册
        //忘记密码
        FORGET_PASSWORD_STEP_ONE,
        FORGET_PASSWORD_STEP_TWO
    }

}
