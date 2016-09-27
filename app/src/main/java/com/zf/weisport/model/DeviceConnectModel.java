package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-05-05 18:20
 * @email Xs.lin@foxmail.com
 */
public class DeviceConnectModel extends BaseModel<DeviceConnectModel> {

    public String ID;
    public String Device_Type;
    public String FullName;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDevice_Type() {
        return Device_Type;
    }

    public void setDevice_Type(String device_Type) {
        Device_Type = device_Type;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
