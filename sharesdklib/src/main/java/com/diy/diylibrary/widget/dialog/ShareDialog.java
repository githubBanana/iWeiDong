package com.diy.diylibrary.widget.dialog;

import android.app.*;
import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.diy.diylibrary.R;
import com.diy.diylibrary.sharesdk.ShareParameters;
import com.diy.diylibrary.sharesdk.SharePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by siushen on 2016/3/17.
 */
public class ShareDialog extends DialogFragment implements View.OnClickListener{

    private String url;
    private String shareConent;
    public static ShareDialog getInstance(String shareContent,String url) {
        ShareDialog shareDialog = new ShareDialog(shareContent,url);
        return shareDialog;
    }

    public ShareDialog(String shareContent,String url) {
        this.shareConent = shareContent;
        this.url = url;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    private OnListenShareDialog mOnListenShareDialog;
    private View view = null;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = LayoutInflater.from(getActivity()).inflate(R.layout.share_layout, null);
        AlertDialog alertDialog = builder.setView(view).create();
        findView();
        /*Window dialogWindow = alertDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);*/
        return alertDialog;
    }

    private TextView shutdownbottom ;
    private TextView shutdowntop;
    private TextView weibo,weixin,moments,qq;
    private void findView() {
        shutdownbottom = (TextView) view.findViewById(R.id.shutdownbottom);
        shutdowntop = (TextView) view.findViewById(R.id.shutdowntop);
        weibo = (TextView) view.findViewById(R.id.img3);
        weixin = (TextView) view.findViewById(R.id.img4);
        moments = (TextView) view.findViewById(R.id.img1);
        qq = (TextView) view.findViewById(R.id.img2);
        qq.setOnClickListener(this);
        moments.setOnClickListener(this);
        weixin.setOnClickListener(this);
        shutdownbottom.setOnClickListener(this);
        shutdowntop.setOnClickListener(this);
        weibo.setOnClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnListenShareDialog = (OnListenShareDialog) activity;
        } catch (ClassCastException e) {
            // TODO: handle exception
            throw new ClassCastException(activity.toString() + " must implement OnListenShareDialog");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.shutdownbottom) {
            dismiss();
        }
        if(id == R.id.shutdowntop) {
            dismiss();
        }
        if(id == R.id.img1) {
            share(ShareParameters.SHARE_WEIBO);
            if (mOnListenShareDialog != null)
                mOnListenShareDialog.OnListenTouch(ShareParameters.SHARE_WEIBO);
            dismiss();
        }
        if(id == R.id.img2) {
            share(ShareParameters.SHARE_WEIXIN);
            if (mOnListenShareDialog != null)
                mOnListenShareDialog.OnListenTouch(ShareParameters.SHARE_WEIXIN);
            dismiss();
        }
        if(id == R.id.img3) {
            share(ShareParameters.SHARE_WEIXIN_MOMENT);
            if (mOnListenShareDialog != null)
                 mOnListenShareDialog.OnListenTouch(ShareParameters.SHARE_WEIXIN_MOMENT);
            dismiss();
        }
        if(id == R.id.img4) {
            share(ShareParameters.SHARE_QQ);
            if (mOnListenShareDialog != null)
                 mOnListenShareDialog.OnListenTouch(ShareParameters.SHARE_QQ);
            dismiss();
        }

    }

    private void share(String platform) {
//        String imageUrl = "http://www.darshancomputing.com/android/1.5-drawables/sym_call_missed.png";
        String imageUrl = "http://www.weidongzn.com/Themes/Images/pic_share.png";
        boolean silent = true;
        List<PackageInfo> packageInfos = getContext().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            if (packageInfos.get(i).packageName.equals("com.sina.weibo")) {
                imageUrl = "http://www.weidongzn.com/Themes/Images/pic_share.png";
                silent = false;
                break;
            }
        }
        Log.e("TGG","微博分享图片链接："+imageUrl);
        Map<String, String> map = new HashMap<>();
        map.put(ShareParameters.TITLE, "i微动");
        map.put(ShareParameters.IMAGE_URL, imageUrl);
        map.put(ShareParameters.TEXT_CONTENT, shareConent+"\n");
        if (url != null) {
            map.put(ShareParameters.URL, url);
        }
        SharePresenter.getPresenter(getActivity()).showShareOnekey(platform, map,silent, getActivity());
    }


    public interface OnListenShareDialog {
        void OnListenTouch(String platform);
    }

}
