package com.zf.weisport.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * Created by CY on 2016/4/12.
 */
public class TopicModel extends BaseModel<TopicModel> implements Parcelable {

    public String ID;// "59",
    public String Content;// "测试图片",
    public String AddTime;// "2016/4/12 9:42:57",
    public String UserID;// "19",
    public String Name;// "Tenooth",
    public String ShareUrl;//分享链接
    public String HeadImg;// "",
    public String Imgs;// "http://wd.zfwsc.com//Files/upload/201604/12/i20160412094257015.jpg,http://wd.zfwsc.com//Files/upload/201604/12/i20160412094257092.jpg,http://wd.zfwsc.com//Files/upload/201604/12/i20160412094257094.jpg,",
    public String IS_Follow;// "0",
    public String IS_Keep;// "0",
    public String Comment;// "0",
    public String CollectCount;// "0",
    public String Label;// "爱运动,"

    public String getID() {
        return ID;
    }

    public String getContent() {
        return Content;
    }

    public String getAddTime() {
        return AddTime;
    }

    public String getUserID() {
        return UserID;
    }

    public String getName() {
        return Name;
    }

    public String getShareUrl() {
        return ShareUrl;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public String getImgs() {
        return Imgs;
    }

    public String getIS_Follow() {
        return IS_Follow;
    }

    public String getIS_Keep() {
        return IS_Keep;
    }

    public String getComment() {
        return Comment;
    }

    public String getCollectCount() {
        return CollectCount;
    }

    public String getLabel() {
        return Label;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setShareUrl(String shareUrl) {
        ShareUrl = shareUrl;
    }

    public void setHeadImg(String headImg) {
        HeadImg = headImg;
    }

    public void setImgs(String imgs) {
        Imgs = imgs;
    }

    public void setIS_Follow(String IS_Follow) {
        this.IS_Follow = IS_Follow;
    }

    public void setIS_Keep(String IS_Keep) {
        this.IS_Keep = IS_Keep;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public void setCollectCount(String collectCount) {
        CollectCount = collectCount;
    }

    public void setLabel(String label) {
        Label = label;
    }

    @Override
    public String toString() {
        return "TopicModel{" +
                "ID='" + ID + '\'' +
                ", Content='" + Content + '\'' +
                ", AddTime='" + AddTime + '\'' +
                ", UserID='" + UserID + '\'' +
                ", Name='" + Name + '\'' +
                ", HeadImg='" + HeadImg + '\'' +
                ", Imgs='" + Imgs + '\'' +
                ", IS_Follow='" + IS_Follow + '\'' +
                ", IS_Keep='" + IS_Keep + '\'' +
                ", Comment='" + Comment + '\'' +
                ", CollectCount='" + CollectCount + '\'' +
                ", Label='" + Label + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.Content);
        dest.writeString(this.AddTime);
        dest.writeString(this.UserID);
        dest.writeString(this.Name);
        dest.writeString(this.HeadImg);
        dest.writeString(this.Imgs);
        dest.writeString(this.IS_Follow);
        dest.writeString(this.IS_Keep);
        dest.writeString(this.Comment);
        dest.writeString(this.CollectCount);
        dest.writeString(this.Label);
    }

    public TopicModel() {
    }

    protected TopicModel(Parcel in) {
        this.ID = in.readString();
        this.Content = in.readString();
        this.AddTime = in.readString();
        this.UserID = in.readString();
        this.Name = in.readString();
        this.HeadImg = in.readString();
        this.Imgs = in.readString();
        this.IS_Follow = in.readString();
        this.IS_Keep = in.readString();
        this.Comment = in.readString();
        this.CollectCount = in.readString();
        this.Label = in.readString();
    }

    public static final Creator<TopicModel> CREATOR = new Creator<TopicModel>() {
        @Override
        public TopicModel createFromParcel(Parcel source) {
            return new TopicModel(source);
        }

        @Override
        public TopicModel[] newArray(int size) {
            return new TopicModel[size];
        }
    };
}
