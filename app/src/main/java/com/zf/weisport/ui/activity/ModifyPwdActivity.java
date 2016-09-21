package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zf.weisport.R;
import com.zf.weisport.databinding.ActivityModifyPwdBinding;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.IModifyPwdCallback;
import com.zf.weisport.ui.viewmodel.ModifyPwdViewModel;

/**
 * @version V1.0 <修改密码界面>
 * @author: Xs
 * @date: 2016-09-21 09:11
 * @email Xs.lin@foxmail.com
 */
public class ModifyPwdActivity extends BaseActivity<ModifyPwdViewModel,ActivityModifyPwdBinding> implements IModifyPwdCallback {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,ModifyPwdActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected ModifyPwdViewModel initViewModel() {
        return new ModifyPwdViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setModifyPwdViewModel(getViewModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd,true);
        /**
         * 提交
         */
        getBinding().btnModifyCommit.setOnClickListener(view -> getViewModel().commit());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.modify_password);
    }

    @Override
    public void onCommitSuccess() {
        finish();
    }
}
