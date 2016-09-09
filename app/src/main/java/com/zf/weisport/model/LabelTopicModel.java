package com.zf.weisport.model;
import com.xs.basic_mvvm.model.BaseModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-08 17:44
 * @email Xs.lin@foxmail.com
 */
public class LabelTopicModel extends BaseModel<LabelTopicModel> {

    public String ID;
    public String Name;
 /*   public List<LabelTopic> Topic;

    public List<LabelTopic> getTopic() {
        return Topic;
    }

    public void setTopic(List<LabelTopic> topic) {
        Topic = topic;
    }*/

    public String getID() {

        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "LabelTopicModel{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Topic="  +
                '}';
    }
}
