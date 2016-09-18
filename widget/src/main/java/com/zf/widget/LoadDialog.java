package com.zf.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 16:50
 * @email Xs.lin@foxmail.com
 */
public class LoadDialog extends Dialog {
    private static LoadDialog mLoadDialog;
    private static TextView mTvTextView;
    public LoadDialog(Context context) {
        super(context, R.style.AppTheme_Loading);
        this.init();
    }

    protected LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.init();
    }

    private void init() {
        this.getWindow().setDimAmount(0.0F);
        View view = this.getLayoutInflater().inflate(R.layout.layout_lib_loading_view, (ViewGroup)null);
        mTvTextView = (TextView) view.findViewById(R.id.textView);
        this.setContentView(view);
    }

    public static LoadDialog showLoadDialog(Context context) {
        showLoadDialog(context, context.getResources().getString(R.string.loadlib_loading));
        return mLoadDialog;
    }

    public static LoadDialog showLoadDialog(Context context, String message) {
        if(mLoadDialog == null) {
            mLoadDialog = new LoadDialog(context);
        }
        mTvTextView.setText(message);
        if(!mLoadDialog.isShowing()) {
            try {
                mLoadDialog.show();
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return mLoadDialog;
    }

    public static void dismissLoadDialog() {
        if(mLoadDialog != null && mLoadDialog.isShowing()) {
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }

    }
}
