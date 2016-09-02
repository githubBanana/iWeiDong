package com.xs.basic_mvvm.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.xs.basic_mvvm.ui.viewmodel.ViewModel;
import com.xs.basic_mvvm.widget.load.LoadingFragment;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-08-17 11:14
 * @email Xs.lin@foxmail.com
 */
public class BaseMvvmActivity<VM extends ViewModel,B extends ViewDataBinding> extends BaseActivity implements ICallBck{

    private B   _b;
    private VM  _vm;

    @Override
    protected View parseLayoutResId(int layoutResID) {
        _vm = initViewModel();
        try {
            _b = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
            toBinding();
            return _b.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.parseLayoutResId(layoutResID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (_vm != null)
            _vm.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_vm != null)
            _vm.onStop();
        dismissLoadingView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (_vm != null)
            _vm.onDestroy();
    }

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


    @Override
    public void showToast(String str) {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(this,getString(resId),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView(@StringRes int resId) {
        LoadingFragment.getLoad(getString(resId)).show(getSupportFragmentManager(),LoadingFragment.TAG);
    }

    @Override
    public void showLoadingView() {
        LoadingFragment.getLoad(null).show(getSupportFragmentManager(),LoadingFragment.TAG);
    }

    @Override
    public void dismissLoadingView() {
        Fragment _fm = getSupportFragmentManager().findFragmentByTag(LoadingFragment.TAG);
        if (_fm != null) {
            DialogFragment _df = (DialogFragment) _fm;
            _df.dismiss();
        }
    }



}
