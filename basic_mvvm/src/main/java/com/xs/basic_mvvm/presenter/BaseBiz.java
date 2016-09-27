package com.xs.basic_mvvm.presenter;

import android.support.annotation.StringRes;

import com.xs.basic_mvvm.R;
import com.xs.basic_mvvm.model.BaseModel;
import com.xs.basic_mvvm.ui.callback.ICallBck;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @version V1.0 <业务层基类>
 * @author: Xs
 * @date: 2016-08-17 15:28
 * @email Xs.lin@foxmail.com
 */
public class BaseBiz<View extends IBaseView> implements IBaseBiz,ICallBck{

    private View _iView;
    private ICallBck _iCallback;
    private CompositeSubscription _compositeSubscription;
    public BaseBiz(View view ,ICallBck callBck) {
        this._iView = view;
        this._iCallback = callBck;
    }

    public <T> T getCallBack() {
        return (T) _iCallback;
    }

    public View getView() {
        return _iView;
    }

    protected <T>Subscriber<T> getSubscriber() {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                dismissLoadingView();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                showToast(R.string.net_error_toast);
                dismissLoadingView();
            }

            @Override
            public void onNext(T t) {
            }
        };
    }

    /**
     * 配置调度器
     * @param observable
     * @param <T>
     * @return
     */
    protected <T>Observable<T> observable(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addSubscription(Subscription subscription) {
        if (_compositeSubscription == null)
            _compositeSubscription = new CompositeSubscription();
        _compositeSubscription.add(subscription);
    }

    public void unSubscribe() {
        if (_compositeSubscription != null && _compositeSubscription.hasSubscriptions())
            _compositeSubscription.unsubscribe();
    }

    /**
     * 验证网络结果
     *
     * @param baseModel
     */
    public boolean assertResult(BaseModel baseModel) {
        if (baseModel.isSuccess())
            return true;
        showToast(baseModel.getErrMsg());
        return false;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }


    @Override
    public void showToast(String str) {
        if (_iCallback != null)
            _iCallback.showToast(str);
    }

    @Override
    public void showToast(@StringRes int resId) {
        if (_iCallback != null)
            _iCallback.showToast(resId);
    }

    @Override
    public void showLoadingView(@StringRes int resId) {
        if (_iCallback != null)
            _iCallback.showLoadingView(resId);
    }

    @Override
    public void showLoadingView() {
        if (_iCallback != null)
            _iCallback.showLoadingView();
    }

    @Override
    public void dismissLoadingView() {
        if (_iCallback != null)
            _iCallback.dismissLoadingView();
    }

    @Override
    public void showTipDialog(String message) {
        if (_iCallback != null)
            _iCallback.showTipDialog(message);
    }

    @Override
    public void loadInitData() {

    }

}
