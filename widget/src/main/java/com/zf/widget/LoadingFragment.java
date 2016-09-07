package com.zf.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-06-04 14:48
 * @email Xs.lin@foxmail.com
 */
public class LoadingFragment extends DialogFragment {

    public static final String TAG = LoadingFragment.class.getSimpleName();
    private static final String LABEL = "message";

    public static LoadingFragment getLoad(String message) {
        final LoadingFragment lf = new LoadingFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(LABEL, message);
        lf.setArguments(bundle);
        return lf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.AppTheme_Loading);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View _view = inflater.inflate(R.layout.layout_lib_loading_view,container,false);
        final String message = getArguments().getString(LABEL);
        final TextView _message = (TextView) _view.findViewById(R.id.textView);
        if (message == null || "".equals(message)){
        } else{
            _message.setText(message);
        }
        return _view;
    }

}
