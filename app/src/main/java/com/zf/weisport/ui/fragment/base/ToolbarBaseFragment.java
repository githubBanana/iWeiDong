package com.zf.weisport.ui.fragment.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.xs.basic_mvvm.ui.viewmodel.ViewModel;
import com.zf.weisport.R;

/**
 * @version V1.0 <toolbar fragment 基类>
 * @author: Xs
 * @date: 2016-09-08 17:31
 * @email Xs.lin@foxmail.com
 */
public abstract class ToolbarBaseFragment<VM extends ViewModel,B extends ViewDataBinding> extends BaseFragment<VM,B> implements Toolbar.OnMenuItemClickListener{

    Toolbar _toolbar;
    AppCompatActivity mAppCompatAct;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * 使能toolbar
         */
        mAppCompatAct = (AppCompatActivity) getActivity();
        setHasOptionsMenu(true);
        _toolbar = (Toolbar) mAppCompatAct.findViewById(R.id.toolbar_default);
        mAppCompatAct.setSupportActionBar(_toolbar);

    }

    protected void setHomeAsUpIndicator(int resId) {
        mAppCompatAct.getSupportActionBar().setHomeAsUpIndicator(resId);
    }

    /**
     * 显示左边图标
     * @param enable
     */
    protected void setDisplayHomeAsUpEnabled(boolean enable) {
        mAppCompatAct.getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    /**
     * 设置主题
     * @param resId
     */
    protected void setTitle(int resId) {
        if (_toolbar != null)
            _toolbar.setTitle(resId);
    }

    protected Toolbar getToolbar() {
        if (_toolbar == null)
            throw new NullPointerException("toolbar should be init");
        return _toolbar;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            mAppCompatAct.onBackPressed();
        return true;
    }
}
