package com.zf.weisport.manager.event;

import com.zf.weisport.model.AddLabelModel;

/**
 * @version V1.0 <UI逻辑通知事件>
 * @author: Xs
 * @date: 2016-03-31 09:15
 * @email Xs.lin@foxmail.com
 */
public class Event<T> {

    public Event(){}

    private int flag ;
    private String value;
    public AddLabelModel addLabelModel;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFlag() {return flag;}

    public void setFlag(int flag) {this.flag = flag;}

    public AddLabelModel getAddLabelModel() {return addLabelModel;}

    public void setAddLabelModel(AddLabelModel addLabelModel) {this.addLabelModel = addLabelModel;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event<?> event = (Event<?>) o;

        if (flag != event.flag) return false;
        if (value != null ? !value.equals(event.value) : event.value != null) return false;
        return addLabelModel != null ? addLabelModel.equals(event.addLabelModel) : event.addLabelModel == null;

    }

    @Override
    public int hashCode() {
        int result = flag;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (addLabelModel != null ? addLabelModel.hashCode() : 0);
        return result;
    }
}
