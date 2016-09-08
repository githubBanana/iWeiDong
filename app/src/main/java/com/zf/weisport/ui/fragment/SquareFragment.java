package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zf.weisport.R;
import com.zf.weisport.ui.fragment.base.ToolbarBaseFragment;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-08 17:24
 * @email Xs.lin@foxmail.com
 */
public class SquareFragment extends ToolbarBaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_square;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getToolbar().setTitle(R.string.hot_square_title_text);
        getToolbar().setOnMenuItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.square_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_pic_photo_id:
                showToast("hello");
                break;
        }
        return true;
    }
}
