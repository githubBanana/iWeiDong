package com.zf.weisport.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * Created by CY on 2016/4/13.
 */
public class TopicCommentModel extends BaseModel<TopicCommentModel> implements Parcelable {
//    {"ErrNum":"0","ErrMsg":"","TotalCount":"1","data":[{"ID":"17","Topic_ID":"59","ToUser_ID":"0","Comment":"哦哦哦","AddTime":"2016/4/13 12:07:42","Name":"","HeadImg":""}]}

//    {
//        "ErrNum": "0",
//            "ErrMsg": "",
//            "TotalCount": "1",
//            "data": [
//        {
//            "ID": "17",
//                "Topic_ID": "59",
//                "ToUser_ID": "0",
//                "Comment": "哦哦哦",
//                "AddTime": "2016/4/13 12:07:42",
//                "Name": "",
//                "HeadImg": ""
//        }
//        ]
//    }

    public String ID;// "17",
    public String Topic_ID;// "59",
    public String ToUser_ID;// "0",
    public String Comment;// "哦哦哦",
    public String AddTime;// "2016/4/13 12:07:42",
    public String Name;// "",
    public String HeadImg;// ""

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTopic_ID() {
        return Topic_ID;
    }

    public void setTopic_ID(String topic_ID) {
        Topic_ID = topic_ID;
    }

    public String getToUser_ID() {
        return ToUser_ID;
    }

    public void setToUser_ID(String toUser_ID) {
        ToUser_ID = toUser_ID;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String headImg) {
        HeadImg = headImg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.Topic_ID);
        dest.writeString(this.ToUser_ID);
        dest.writeString(this.Comment);
        dest.writeString(this.AddTime);
        dest.writeString(this.Name);
        dest.writeString(this.HeadImg);
    }

    public TopicCommentModel() {
    }

    protected TopicCommentModel(Parcel in) {
        this.ID = in.readString();
        this.Topic_ID = in.readString();
        this.ToUser_ID = in.readString();
        this.Comment = in.readString();
        this.AddTime = in.readString();
        this.Name = in.readString();
        this.HeadImg = in.readString();
    }

    public static final Creator<TopicCommentModel> CREATOR = new Creator<TopicCommentModel>() {
        @Override
        public TopicCommentModel createFromParcel(Parcel source) {
            return new TopicCommentModel(source);
        }

        @Override
        public TopicCommentModel[] newArray(int size) {
            return new TopicCommentModel[size];
        }
    };
}
