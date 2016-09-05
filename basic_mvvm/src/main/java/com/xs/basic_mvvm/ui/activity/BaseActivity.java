package com.xs.basic_mvvm.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xs.basic_mvvm.R;

/**
 * @version V1.0 <Toolbar Base Activity>
 * @author: Xs
 * @date: 2016-08-17 11:15
 * @email Xs.lin@foxmail.com
 */
public class BaseActivity extends AppCompatActivity{

    /**
     * 标题参数
     */
    public static final String EXTRA_TITLE_NAME = "title_name";

    private Toolbar     _toolbar;
    private TextView    _tvTitle;
    @Override
    public void setContentView(int layoutResID) {
        try {
            View view = parseLayoutResId(layoutResID);
            setContentView(view,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContentView(int layoutResID, boolean isToobar) {
        View view = parseLayoutResId(layoutResID);
        setContentView(view, isToobar);
    }

    /**
     * 解析布局文件
     *
     * @param layoutResID
     * @return
     */
    protected View parseLayoutResId(int layoutResID) {
        return getLayoutInflater().inflate(layoutResID,null);
    }

    /**
     * 默认使用toolbar
     * @return
     */
    protected boolean isHasToobar() {
        return true;
    }

    /**
     * 初始化布局
     * @param view
     * @param isToobar
     */
    protected void setContentView(View view, boolean isToobar) {
        if (isToobar) {
            ViewGroup contentView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.layout_toolbar, null);
            _toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
            _tvTitle = (TextView) contentView.findViewById(R.id.tv_toolbar);
            contentView.addView(view, new ViewGroup.LayoutParams(-1, -1));
            setSupportActionBar(_toolbar);
            _toolbar.setCollapsible(false);
            showUpEnabled(true);
            getSupportActionBar().setTitle(null);
            super.setContentView(contentView);
        } else {
            super.setContentView(view);
        }
        final String title = getIntent().getStringExtra(EXTRA_TITLE_NAME);
        if (!TextUtils.isEmpty(title))
            setTitle(title);
    }

    /**
     * 设置左边返回图标
     * @param enable
     */
    protected void showUpEnabled(boolean enable) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
//        getSupportActionBar().setHomeAsUpIndicator();
    }

    /**
     * 设置左边 bar title
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        if (_toolbar != null) {
            getSupportActionBar().setTitle(title);
        } else {
            super.setTitle(title);
        }

    }

    @Override
    public void setTitle(int titleId) {
        if (_toolbar != null) {
            getSupportActionBar().setTitle(titleId);
        } else {
            super.setTitle(titleId);
        }
    }

    /**
     * 设置居中 bar title
     * @param title
     */
    protected void setCenterTitle(CharSequence title) {
        if (_toolbar != null)
            _tvTitle.setText(title);
        else
            super.setTitle(title);
    }

    protected void setCenterTitle(int titleId) {
        if (_toolbar != null)
            _tvTitle.setText(titleId);
        else
            super.setTitle(titleId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
