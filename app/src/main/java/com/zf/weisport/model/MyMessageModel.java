package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-08 19:33
 * @email Xs.lin@foxmail.com
 */
public class MyMessageModel extends BaseModel<MyMessageModel> {

    public String rowNum;
    public String ID;
    public String Title;
    public String AddTime;
    public String Url;
    public String HadRead;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getHadRead() {
        return HadRead;
    }

    public void setHadRead(String hadRead) {
        HadRead = hadRead;
    }

    @Override
    public String toString() {
        return "MyMessageModel{" +
                "rowNum='" + rowNum + '\'' +
                ", ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", AddTime='" + AddTime + '\'' +
                ", Url='" + Url + '\'' +
                ", HadRead='" + HadRead + '\'' +
                '}';
    }
}
