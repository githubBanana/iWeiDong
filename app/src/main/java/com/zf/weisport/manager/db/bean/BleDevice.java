package com.zf.weisport.manager.db.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-11 15:31
 * @email Xs.lin@foxmail.com
 */
@DatabaseTable(tableName = BleDevice.TABLE_NAME)
public class BleDevice implements Serializable {

//    public static String cacheAddress = null;
    public static String cacheDeviceId = "0";//无用户登录
//    public static String cacheDeviceName = null;
    public static String cacheDeviceType = "0";//无用户登录


 /*   public static void setCurrDeviceCache_Id_Type(String deviceId,String type) {
        cacheDeviceId = deviceId;
        cacheDeviceType = type;
    }
    public static void setCurrDeviceCache_Name_Address(String address,String deviceName) {
        cacheAddress = address;
        cacheDeviceName = deviceName;
    }*/

    public static final String TABLE_NAME = "tb_bledevice";

    public static final String TYPE = "type";
    public static final String ADDRESS = "address";
    public static final String NAME = "name";
    public static final String ID = "id";//本地递增ID
    public static final String NET_ID = "net_id";//用户ID
    public static final String LAST_CONNECTED = "last_connected";

    @DatabaseField(columnName = TYPE)
    private String type;
    @DatabaseField(generatedId = true,columnName = ID)
    private int id;
    @DatabaseField(columnName = ADDRESS)
    private String address ;
    @DatabaseField(columnName = NAME)
    private String name;
    @DatabaseField(columnName = NET_ID)
    private String netId ;
    @DatabaseField(columnName = LAST_CONNECTED)
    private boolean last_connected;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLast_connected() {
        return last_connected;
    }

    public void setLast_connected(boolean last_connected) {
        this.last_connected = last_connected;
    }

    @Override
    public String toString() {
        return "BleDevice{" +
                "type=" + type +
                ", id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", netId='" + netId + '\'' +
                ", last_connected=" + last_connected +
                '}';
    }
}
