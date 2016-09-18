package com.zf.weisport.manager.util;


import com.zf.weisport.model.MyFollowListModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<MyFollowListModel> {

	public int compare(MyFollowListModel o1, MyFollowListModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
