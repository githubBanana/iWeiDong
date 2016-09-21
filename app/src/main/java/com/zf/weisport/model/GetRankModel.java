package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-15 14:04
 * @email Xs.lin@foxmail.com
 */
public class GetRankModel extends BaseModel<GetRankModel> {
//{"ID":"76","Name":"mars","Speed":"10004","FilePath":"http://www.baidu.com.xx.jpg","ItemName":"荣耀黄金"}
    public String ID;
    public String Name;
    public String Speed;
    public String FilePath;
    public String ItemName;

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

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    @Override
    public String toString() {
        return "GetRankModel{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Speed='" + Speed + '\'' +
                ", FilePath='" + FilePath + '\'' +
                ", ItemName='" + ItemName + '\'' +
                '}';
    }
}
