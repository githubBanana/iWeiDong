package com.zf.weisport.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ExpandableListView;

import com.zf.weisport.R;
import com.zf.weisport.adapter.EquipExpandableListAdapter;
import com.zf.weisport.manager.db.bean.BleDevice;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设备配件 界面
 */
public class EquipsActivity extends BaseActivity{

    @Bind(R.id.expandableListView_equipId)
    ExpandableListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equips,true);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 从数据库获取数据 填充到listview
     */
    private void initView() {

        ArrayList<String> groups = new ArrayList<>();
        ArrayList<String> childs = new ArrayList<>();
        List<BleDevice> list = AppDatabaseCache.getCache(this).getBleDeviceList(BleDevice.cacheDeviceType);
        groups.add("");
        for (int i = 0; i < list.size(); i++) {
            childs.add(list.get(i).getName());
        }

        final EquipExpandableListAdapter mAdapter = new EquipExpandableListAdapter(this,groups,childs);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.tv_equip);
    }


}
