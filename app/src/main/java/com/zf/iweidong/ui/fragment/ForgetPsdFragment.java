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
 * @date: 2016-09-05 15:34
 * @email Xs.lin@foxmail.com
 */
public class ForgetPsdFragment extends BaseFragment {
    private static final String TAG = "ForgetPsdFragment";

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
    public void onResume() {
        super.onResume();
        mOnChangeTitle.onChangeCenterTitle(R.string.forget_psd_title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        if (!(getActivity() instanceof View.OnClickListener)) {
            throw new IllegalStateException("activity not implement View.OnClickListener");
        }

        view.findViewById(R.id.btn_next).setOnClickListener((View.OnClickListener) getActivity());
    }
}
