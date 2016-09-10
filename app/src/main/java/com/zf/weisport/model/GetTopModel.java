package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-07 10:42
 * @email Xs.lin@foxmail.com
 */
public class GetTopModel extends BaseModel<GetTopModel> {

    public String ID;
    public String FilePath;
    public String Title;
    public String url;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GetTopModel{" +
                "ID='" + ID + '\'' +
                ", FilePath='" + FilePath + '\'' +
                ", Title='" + Title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
