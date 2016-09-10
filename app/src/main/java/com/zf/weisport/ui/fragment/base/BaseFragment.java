package com.zf.weisport.ui.fragment.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xs.basic_mvvm.ui.fragment.BaseMvvmFragment;
import com.xs.basic_mvvm.ui.viewmodel.ViewModel;
import com.zf.weisport.ui.callback.IBaseCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-08-31 18:12
 * @email Xs.lin@foxmail.com
 */
public abstract class BaseFragment<VM extends ViewModel,B extends ViewDataBinding> extends BaseMvvmFragment<VM,B> implements IBaseCallback {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDataLoadSuccess() {

    }

    @Override
    public void onSubmitCompleted(Object object) {

    }

    @Override
    public void onNetError() {

    }

    @Override
    public void onNetEmpty() {

    }
}
