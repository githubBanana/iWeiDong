package com.zf.iweidong.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zf.iweidong.R;
import com.zf.iweidong.listener.OnChangeTilte;
import com.zf.iweidong.ui.fragment.base.BaseFragment;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 13:55
 * @email Xs.lin@foxmail.com
 */
public class RegisterFragment extends BaseFragment {
    private static final String TAG = "RegisterFragment";

    public RegisterFragment() {
        // Required empty public constructor
    }

    OnChangeTilte mOnChangeTitle;

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
    protected int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnChangeTitle.onChangeCenterTitle(R.string.register_title);
    }
}
