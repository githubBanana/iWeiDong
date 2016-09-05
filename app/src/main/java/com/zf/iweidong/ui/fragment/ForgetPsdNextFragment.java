package com.zf.iweidong.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.iweidong.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 15:50
 * @email Xs.lin@foxmail.com
 */
public class ForgetPsdNextFragment extends Fragment {

    public ForgetPsdNextFragment(){

    }

/*    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forget_psd_next;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_psd_next,container,false);
    }
}
