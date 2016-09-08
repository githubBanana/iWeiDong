package com.diy.diylibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.diy.diylibrary.R;
import com.diy.diylibrary.sharesdk.ShareParameters;
import com.diy.diylibrary.sharesdk.SharePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/17.
 */
public class SheetDialog implements View.OnClickListener {


    private Display display;
    private Context context;
    private OnListenSheetDialog mOnlistenSheetDialog;

    private String url;
    private String shareConent;

    public SheetDialog(Context context,String shareConent) {
        this.shareConent = shareConent;
        this.context = context;
        if (context instanceof OnListenSheetDialog) {
            this.mOnlistenSheetDialog = (OnListenSheetDialog) context;
        }
        initDialogManager();

    }

    public SheetDialog(Context context,String shareConent,String url) {
        this.shareConent = shareConent;
        this.context = context;
        this.url = url;
        if (context instanceof OnListenSheetDialog) {
            this.mOnlistenSheetDialog = (OnListenSheetDialog) context;
        }
       initDialogManager();
    }

    private void initDialogManager() {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    private Dialog dialog;

    public SheetDialog builder() {
        view = LayoutInflater.from(context).inflate(R.layout.share_sheet_layout, null);

        view.setMinimumWidth(display.getWidth());
        findView();

        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    private View view;
    private TextView shutdownbottom;
    private TextView weibo, weixin, moments, qq;

    private void findView() {
        shutdownbottom = (TextView) view.findViewById(R.id.shutdownbottom);
        weibo = (TextView) view.findViewById(R.id.img3);
        weixin = (TextView) view.findViewById(R.id.img4);
        moments = (TextView) view.findViewById(R.id.img1);
        qq = (TextView) view.findViewById(R.id.img2);
        qq.setOnClickListener(this);
        moments.setOnClickListener(this);
        weixin.setOnClickListener(this);
        shutdownbottom.setOnClickListener(this);
        weibo.setOnClickListener(this);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.shutdownbottom) {
            dialog.dismiss();
        } else if (id == R.id.img1) {
            share(ShareParameters.SHARE_WEIBO);
            if (mOnlistenSheetDialog != null) {
                mOnlistenSheetDialog.OnListenTouchSheetDialog(ShareParameters.SHARE_WEIBO);
            }
        } else if (id == R.id.img2) {
            share(ShareParameters.SHARE_WEIXIN);
            if (mOnlistenSheetDialog != null) {
                mOnlistenSheetDialog.OnListenTouchSheetDialog(ShareParameters.SHARE_WEIXIN);
            }
        } else if (id == R.id.img3) {
            share(ShareParameters.SHARE_WEIXIN_MOMENT);
            if (mOnlistenSheetDialog != null) {
                mOnlistenSheetDialog.OnListenTouchSheetDialog(ShareParameters.SHARE_WEIXIN_MOMENT);
            }
        } else if (id == R.id.img4) {
            share(ShareParameters.SHARE_QQ);
            if (mOnlistenSheetDialog != null) {
                mOnlistenSheetDialog.OnListenTouchSheetDialog(ShareParameters.SHARE_QQ);
            }
        }
    }


    private void share(String platform) {
        boolean silent = true;
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            if (packageInfos.get(i).packageName.equals("com.sina.weibo")) {
                silent = false;
                break;
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put(ShareParameters.TITLE, "i微动");
        map.put(ShareParameters.IMAGE_URL, "http://www.weidongzn.com/Themes/Images/pic_share.png");
        map.put(ShareParameters.TEXT_CONTENT, shareConent+"\n");
        if (url != null) {
            map.put(ShareParameters.URL, url);
        }
        SharePresenter.getPresenter(context).showShareOnekey(platform, map,silent, context);
    }

    public interface OnListenSheetDialog {
        void OnListenTouchSheetDialog(String platformName);
    }
}
