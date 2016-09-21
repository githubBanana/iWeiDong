package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diy.diylibrary.sharesdk.AuthorizeLogin;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.CacheManager;
import com.zf.weisport.manager.util.FileUtils;
import com.zf.weisport.manager.util.UserUtil;
import com.zf.weisport.ui.activity.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 17:31
 * @email Xs.lin@foxmail.com
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.bind_account_tv)
    TextView mBindAccount;
    @Bind(R.id.modify_password_tv)
    TextView mModifyPwd;
    //缓存大小
    private String      mCacheSize;
    private String[]    caches;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting,true);
        ButterKnife.bind(this);
        getCacheSize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.setting_title_text);
        modifyOrBind();
    }

    /**
     * 若用户手机号为空 则需要绑定，反之，允许修改密码
     */
    private void modifyOrBind() {
        if (TextUtils.isEmpty(User.getUser().getPhone())) {
            mModifyPwd.setVisibility(View.GONE);
            mBindAccount.setVisibility(View.VISIBLE);
        } else {
            mModifyPwd.setVisibility(View.VISIBLE);
            mBindAccount.setVisibility(View.GONE);
        }
    }

    @OnClick({
            R.id.aboutus_tv,
            R.id.software_update_tv,
            R.id.clear_cahce_tv,
            R.id.bind_account_tv,
            R.id.modify_password_tv,
            R.id.logout
    })
    public void settingOnclick(View view) {
        switch (view.getId()) {
            case R.id.aboutus_tv:
                WebActivity.start(this, RequestHelper.ABOUT_US,WebActivity.WEB_ABOUTUS);
                break;
            case R.id.software_update_tv:
                WebActivity.start(this,WebActivity.INSTALL_URL,WebActivity.WEB_INSTALL_UPDATE);
                break;
            case R.id.clear_cahce_tv:
                showCacheSizeView(view);
                break;
            case R.id.bind_account_tv:
                BindPhoneActivity.start(this);
                break;
            case R.id.modify_password_tv:
                ModifyPwdActivity.start(this);
                break;
            case R.id.logout:
                showLogoutTipView();
                break;
        }
    }

    /**
     * 获取缓存大小
     */
    private void getCacheSize() {
        //getCacheDir().getAbsolutePath()
        caches = new String[]{FileUtils.getLogDir(this), FileUtils.getCacheDir(this), Glide.getPhotoCacheDir(this).getAbsolutePath()};
        Log.e("info", "getCacheSize: "+Glide.getPhotoCacheDir(this).getAbsolutePath() );
        new CacheManager().justCheckSize(caches, size -> mCacheSize = size);
    }

    /**
     * 缓存大小显示
     * @param v
     */
    private void showCacheSizeView(View v) {
        new AlertDialog.Builder(this).setTitle(getString(R.string.cache_clear_text)).setMessage(mCacheSize)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    new CacheManager().justClear(caches, () -> {
                        Snackbar.make(v,getString(R.string.clear_cache_string),Snackbar.LENGTH_SHORT).show();
                        mCacheSize = "0.0B";
                    });
                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    /**
     * 退出账户
     */
    private void showLogoutTipView() {
        new AlertDialog.Builder(this).setTitle(R.string.text_tips).setMessage(R.string.logout_message)
                .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                    logoutSystem();
                    AccountActivity.start(this);
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

    private void logoutSystem() {
        AuthorizeLogin.getPresenter(this).cancleAuthorize(QQ.NAME);
        AuthorizeLogin.getPresenter(this).cancleAuthorize(SinaWeibo.NAME);
        AuthorizeLogin.getPresenter(this).cancleAuthorize(Wechat.NAME);
        UserUtil.setLoginOut();
        AppDatabaseCache.getCache(this).deleteUser();
    }
}
