package com.zf.iweidong.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zf.iweidong.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-02 16:50
 * @email Xs.lin@foxmail.com
 */
public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";

    @Bind(R.id.tv_test)
    TextView teset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        teset.setText("nihadddddddddddddoj");
    }

}
