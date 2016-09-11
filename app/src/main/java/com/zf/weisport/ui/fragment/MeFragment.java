package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zf.weisport.R;
import com.zf.weisport.ui.fragment.base.ToolbarBaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  @version V1.0 <我的 界面>
 * Created by Xs on 2016/9/11.
 */
public class MeFragment extends ToolbarBaseFragment{

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getToolbar().setTitle(R.string.mainTab_me);
        getToolbar().setOnMenuItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.me_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onMenuItemClick(item);
    }

    @OnClick({
            R.id.tv_mine_item1,
            R.id.tv_mine_item2,
            R.id.tv_mine_item3,
            R.id.tv_mine_item4,
            R.id.tv_mine_item5,
            R.id.tv_mine_item6,
            R.id.tv_mine_item7,
            R.id.head_nameId,
            R.id.headId
    })
    public void meOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv_mine_item1:

                break;
            case R.id.tv_mine_item2:

                break;
            case R.id.tv_mine_item3:

                break;
            case R.id.tv_mine_item4:

                break;
            case R.id.tv_mine_item5:

                break;
            case R.id.tv_mine_item6:

                break;
            case R.id.tv_mine_item7:

                break;
            case R.id.head_nameId:

                break;
            case R.id.headId:

                break;
        }
    }

}
