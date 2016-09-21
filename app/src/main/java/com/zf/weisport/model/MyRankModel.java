package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-15 11:31
 * @email Xs.lin@foxmail.com
 */
public class MyRankModel extends BaseModel<MyRankModel> {

    public String Num;

    public String LevelId;

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getLevelId() {
        return LevelId;
    }

    public void setLevelId(String levelId) {
        LevelId = levelId;
    }
}
