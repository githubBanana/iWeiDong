package me.nereo.multi_image_selector.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import me.nereo.multi_image_selector.R;


/**
 * Created by Administrator on 2016/3/17.
 *
 */
public class PhotoDialog implements View.OnClickListener{


    private Display display;
    private Context context;
    private OnListenPhotoDialog mOnlistenPhotoDialog;
    public PhotoDialog(Context context) {
        this.context = context;
        this.mOnlistenPhotoDialog = (OnListenPhotoDialog) context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    private Dialog dialog;
    public PhotoDialog builder() {
        view = LayoutInflater.from(context).inflate(R.layout.photo_layout, null);

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
    private void findView() {
        view.findViewById(R.id.takepicId).setOnClickListener(this);
        view.findViewById(R.id.photoId).setOnClickListener(this);
        view.findViewById(R.id.cancleId).setOnClickListener(this);
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
        if(id == R.id.cancleId) {
            mOnlistenPhotoDialog.OnListenTouchCancle();
            dialog.dismiss();
        }
        if(id == R.id.takepicId) {
            mOnlistenPhotoDialog.OnListenTouchTakePic();
            dialog.dismiss();
        }
        if(id == R.id.photoId) {
            mOnlistenPhotoDialog.OnListenTouchPhoto();
            dialog.dismiss();
        }

    }

    public interface OnListenPhotoDialog {
        void OnListenTouchPhoto();
        void OnListenTouchCancle();
        void OnListenTouchTakePic();
    }
}
