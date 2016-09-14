package com.zf.weisport.manager.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zf.weisport.R;

/**
 * Created by siushen on 2016/3/23.
 * 选择设备
 *
 */
public class SelectDeviceDialog extends DialogFragment implements View.OnClickListener{

    public static final String TAG = SelectDeviceDialog.class.getSimpleName();
    private OnListenSelectDevice mOnListenDevice;
    public static SelectDeviceDialog getInstance() {
        return new SelectDeviceDialog();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnListenDevice = (OnListenSelectDevice) activity;
        } catch (ClassCastException e) {
            // TODO: handle exception
            throw new ClassCastException(activity.toString() + " must implement OnListenSelectDevice");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    private View view;
    private TextView tv1,tv2,tv3,tv4;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = LayoutInflater.from(getActivity()).inflate(R.layout.select_device_layout,null);
        AlertDialog dialog = builder.setView(view).create();
        findView();
        return dialog;
    }
    private void findView(){
        tv1 = (TextView) view.findViewById(R.id.select_tv1);
        tv2 = (TextView) view.findViewById(R.id.select_tv2);
        tv3 = (TextView) view.findViewById(R.id.select_tv3);
        tv4 = (TextView) view.findViewById(R.id.select_tv4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int item = 0;
        String[] sports = getActivity().getResources().getStringArray(R.array.equipCategory);
        String sport="";
        switch (v.getId()) {
            case R.id.select_tv1:
                item = 1;
                break;
            case R.id.select_tv2:
                item = 2;
                break;
            case R.id.select_tv3:
                item = 3;
                break;
            case R.id.select_tv4:
                item = 4;
                break;
        }
        sport=sports[item-1];
        mOnListenDevice.OnListenDeviceCallBack(sport,item);
        dismiss();
    }

    public interface OnListenSelectDevice {

        void OnListenDeviceCallBack(String sport, int selectItem);

    }
}
