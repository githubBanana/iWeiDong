package com.zf.weisport.adapter;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zf.weisport.R;
import com.zf.weisport.manager.util.ImageLoaderUtil;
import com.zf.weisport.manager.util.StringUtils;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.MyFollowListModel;

import java.util.List;

public class MyFollowListAdapter extends BaseAdapter implements SectionIndexer{
	private List<MyFollowListModel> list = null;
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<MyFollowListModel> list){
		this.list = list;
		notifyDataSetChanged();
	}


	public int getCount() {
        if (list!=null)
		return this.list.size();
        return 0;
	}

	public MyFollowListModel getItem(int position) {

		return list.get(position);

	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final MyFollowListModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(arg2.getContext()).inflate(R.layout.item_list_follow, null);
			viewHolder.name = (TextView) view.findViewById(R.id.item_name);
			viewHolder.catalog = (TextView) view.findViewById(R.id.catalog);
			viewHolder.headImg=(ImageView)view.findViewById(R.id.img_profile);
			viewHolder.item_content=(TextView)view.findViewById(R.id.item_content);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.catalog.setVisibility(View.VISIBLE);
			viewHolder.catalog.setText(mContent.getSortLetters());
		}else{
			viewHolder.catalog.setVisibility(View.GONE);
		}
		viewHolder.name.setText(getItem(position).getName());
        ImageLoaderUtil.showRoundComponent(getItem(position).getHeadImg(),viewHolder.headImg);
        int  topicCount = getItem(position).getTopicCount();
        //
        int start = 3;
        SpannableStringBuilder highlight =
                StringUtils.highlightLimit(UIUtil.getString(R.string.show_acts,topicCount), Color.GREEN,  start, start +(topicCount+"").length());
        viewHolder.item_content.setText(highlight);
		return view;
	}
	final static class ViewHolder {
		TextView catalog;
		TextView name;
		ImageView headImg;
        TextView item_content;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 *
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}