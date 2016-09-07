package com.xs.basic_mvvm.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.xs.basic_mvvm.ui.viewmodel.ViewModel;
import com.zf.widget.LoadingFragment;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-08-31 17:43
 * @email Xs.lin@foxmail.com
 */
public abstract class BaseMvvmFragment<VM extends ViewModel,B extends ViewDataBinding> extends Fragment implements ICallBck {

    private B   _b;
    private VM  _vm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return parseLayoutResId(inflater);
    }

    /**
     * 获取布局资源
     */
    protected abstract int getLayoutResId();

    /**
     *  init ViewModel
     */
    protected VM initViewModel() {
        return null;
    }

    /**
     * to bind viewmodel and viewdatabinding
     */
    protected void toBinding() {

    }

    protected VM getViewModel() {
        if (_vm == null)
            throw new NullPointerException("you should init ViewModel");
        return _vm;
    }

    protected B getBinding() {
        if (_b == null)
            throw new NullPointerException("you should init binding");
        return _b;
    }

    /**
     * 解析布局
     * @param inflater
     * @return
     */
    private View parseLayoutResId(LayoutInflater inflater) {
        int layoutResId = getLayoutResId();
        _vm = initViewModel();
        try {
            _b = DataBindingUtil.inflate(inflater, layoutResId, null, false);
            toBinding();
            return _b.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inflater.inflate(layoutResId,null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (_vm != null)
            _vm.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (_vm != null)
            _vm.onStop();
        dismissLoadingView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (_vm != null)
            _vm.onDestroy();
    }


    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(getActivity(),getString(resId),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView(@StringRes int resId) {
        LoadingFragment.getLoad(getString(resId)).show(getActivity().getSupportFragmentManager(),LoadingFragment.TAG);
    }

    @Override
    public void showLoadingView() {
        LoadingFragment.getLoad(null).show(getActivity().getSupportFragmentManager(),LoadingFragment.TAG);
    }

    @Override
    public void dismissLoadingView() {
        Fragment _fm = getActivity().getSupportFragmentManager().findFragmentByTag(LoadingFragment.TAG);
        if (_fm != null) {
            /*DialogFragment _df = (DialogFragment) _fm;
            _df.dismiss();*/
            DialogFragment _df = (DialogFragment) _fm;
            _df.dismiss();
        }
    }
}
