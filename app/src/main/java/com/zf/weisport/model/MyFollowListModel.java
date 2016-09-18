package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

import java.util.Comparator;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-08 19:54
 * @email Xs.lin@foxmail.com
 */
public class MyFollowListModel extends BaseModel<MyFollowListModel> implements Comparator {

    /**
     * ID : 19
     * Name : Tenooth
     * HeadImg :
     * TopicCount : 5
     */
    private String ID;
    private String Name;
    private String HeadImg;
    private int TopicCount;

    private String sortLetters;
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String HeadImg) {
        this.HeadImg = HeadImg;
    }

    public int getTopicCount() {
        return TopicCount;
    }

    public void setTopicCount(int TopicCount) {
        this.TopicCount = TopicCount;
    }

    @Override
    public int compare(Object lhs, Object rhs) {
        return 0;
    }
}
