package com.xs.basic_mvvm.ui.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.StringRes;

import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.xs.basic_mvvm.ui.callback.ILifeCycle;
import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.presenter.IBaseView;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-08-17 14:07
 * @email Xs.lin@foxmail.com
 */
public class ViewModel<CallBack extends ICallBck> extends BaseObservable
        implements ICallBck,ILifeCycle,IBaseView{

    private CallBack _callback;
    private final BaseBiz _baseBiz;

    public ViewModel(CallBack callBack) {
        this._callback = callBack;
        _baseBiz = createBiz();
    }

    /**
     * 业务层
     * @return
     */
    protected  BaseBiz createBiz(){
        return null;
    }

    protected CallBack getCallback() {
        return _callback;
    }

    protected <T extends BaseBiz> T getBiz(){
        return (T) _baseBiz;
    }

    @Override
    public void showToast(String str) {
        if (_callback != null)
            _callback.showToast(str);
    }

    @Override
    public void showToast(@StringRes int resId) {
        if (_callback != null)
            _callback.showToast(resId);
    }

    @Override
    public void showLoadingView(@StringRes int resId) {
        if (_callback != null)
            _callback.showLoadingView(resId);
    }

    @Override
    public void showLoadingView() {
        if (_callback != null)
            _callback.showLoadingView();
    }

    @Override
    public void dismissLoadingView() {
        if (_callback != null)
            _callback.dismissLoadingView();
    }

    @Override
    public void onStart() {
        if (_baseBiz != null)
            _baseBiz.onStart();
    }

    @Override
    public void onStop() {
        if (_baseBiz != null)
            _baseBiz.onStop();
    }

    @Override
    public void onDestroy() {
        if (_baseBiz != null)
            _baseBiz.onDestroy();
    }
}
