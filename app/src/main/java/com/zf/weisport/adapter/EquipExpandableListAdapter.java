package com.zf.weisport.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zf.weisport.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/11.
 */
public class EquipExpandableListAdapter extends BaseExpandableListAdapter {

    /**
     * 设备类型集合
     */
    private ArrayList<String>   groupList;
    /**
     * 设备地址集合
     */
    private ArrayList<String>   childList;
    private Activity            mAct;

    public EquipExpandableListAdapter(Activity activity,ArrayList<String> groupList, ArrayList<String> childList) {
        this.mAct = activity;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolderGroup holderGroup;
        if (convertView == null) {
            holderGroup = new ViewHolderGroup();
            view = mAct.getLayoutInflater().inflate(R.layout.item_equip_group_layout, null);
            holderGroup.mNumber = (TextView) view.findViewById(R.id.equip_address_numberId);
            view.setTag(holderGroup);
        } else {
            view = convertView;
            holderGroup = (ViewHolderGroup) view.getTag();
        }
        holderGroup.mNumber.setText("" + childList.size());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolderChild holderChild;
        if (convertView == null) {
            holderChild = new ViewHolderChild();
            view = mAct.getLayoutInflater().inflate(R.layout.item_equip_child_layout, null);
            holderChild.mChildEquip = (TextView) view.findViewById(R.id.equip_item_nameId);
            holderChild.mConnInfor = (TextView) view.findViewById(R.id.equip_item_infoId);
            view.setTag(holderChild);
        } else {
            view = convertView;
            holderChild = (ViewHolderChild) view.getTag();
        }
        holderChild.mChildEquip.setText(childList.get(childPosition));
        return view;
    }

    final class ViewHolderGroup {
        private ImageView mImageView;
        private TextView mEquipName;
        private TextView mNumber;
    }

    final class ViewHolderChild {
        private TextView mChildEquip;
        private TextView mConnInfor;
    }

}