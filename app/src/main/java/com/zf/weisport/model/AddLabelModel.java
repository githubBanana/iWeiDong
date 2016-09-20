package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-08 10:26
 * @email Xs.lin@foxmail.com
 */
public class AddLabelModel extends BaseModel<AddLabelModel> {
    public String ID;
    public String Name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddLabelModel that = (AddLabelModel) o;

        if (ID != null ? !ID.equals(that.ID) : that.ID != null) return false;
        return Name != null ? Name.equals(that.Name) : that.Name == null;

    }

    @Override
    public int hashCode() {
        int result = ID != null ? ID.hashCode() : 0;
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        return result;
    }
}
