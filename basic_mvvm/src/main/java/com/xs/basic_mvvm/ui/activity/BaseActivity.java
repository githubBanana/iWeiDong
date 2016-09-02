package com.xs.basic_mvvm.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
            Toolbar toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
            contentView.addView(view, new ViewGroup.LayoutParams(-1, -1));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            super.setContentView(contentView);
        } else {
            super.setContentView(view);
        }
        final String title = getIntent().getStringExtra(EXTRA_TITLE_NAME);
        if (!TextUtils.isEmpty(title))
            setTitle(title);
    }

    /**
     * 设置bar主题
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
