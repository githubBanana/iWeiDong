package com.zf.weisport.manager.db.bean;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @version V1.0 <用户>
 * @author: Xs
 * @date: 2016-03-30 14:30
 * @email Xs.lin@foxmail.com
 */

@DatabaseTable(tableName = User.TABLE_NAME)
public class User implements Serializable {
    public static final String TABLE_NAME = "tb_user";

    public static final String DB_ID = "id";
    public static final String NET_ID = "net_id";
    public static final String IS_SYNC = "is_sync";
    public static final String M_OPENID = "m_openid";
    public static final String Q_OPENID = "q_openid";
    public static final String W_OPENID = "w_openid";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String HEAD_URL = "head_url";
    public static final String LEVLE = "level";
    public static final String SEX = "sex";

    @DatabaseField(generatedId = true,columnName = DB_ID)
    private int db_id;
    @DatabaseField(canBeNull = false,columnName = NET_ID)
    private String net_id;
    @DatabaseField(columnName = IS_SYNC)
    private String IS_Sync;
    @DatabaseField(columnName = M_OPENID)
    private String MOpenId;
    @DatabaseField(columnName = Q_OPENID)
    private String QOpenId;
    @DatabaseField(columnName = W_OPENID)
    private String WOpenId;
    @DatabaseField(columnName = NAME)
    private String name ;
    @DatabaseField(columnName = PHONE)
    private String phone;
    @DatabaseField(columnName = HEAD_URL)
    private String headUrl;//对应服务器端的Head_Img
    @DatabaseField(columnName = LEVLE)
    private String Level;
    @DatabaseField(columnName = SEX)
    private String sex;


    private static User instance = null;
    public User(){}
    public static User getUser() {
        if (instance == null) {
            synchronized (User.class) {
                if(instance == null) {
                    instance = new User();
                }
            }
        }
        return instance;
    }

    public void clear() {
        instance = null;
    }

    public void setUser(User user) {
        instance = user;
    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    public String getId() {
        return TextUtils.isEmpty(net_id) ? "0" : net_id;
    }

    public void setId(String net_id) {
        this.net_id = net_id;
    }

    public String getIS_Sync() {
        return IS_Sync;
    }

    public void setIS_Sync(String IS_Sync) {
        this.IS_Sync = IS_Sync;
    }

    public String getMOpenId() {
        return MOpenId;
    }

    public void setMOpenId(String MOpenId) {
        this.MOpenId = MOpenId;
    }

    public String getQOpenId() {
        return QOpenId;
    }

    public void setQOpenId(String QOpenId) {
        this.QOpenId = QOpenId;
    }

    public String getWOpenId() {
        return WOpenId;
    }

    public void setWOpenId(String WOpenId) {
        this.WOpenId = WOpenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "db_id=" + db_id +
                ", net_id='" + net_id + '\'' +
                ", IS_Sync='" + IS_Sync + '\'' +
                ", MOpenId='" + MOpenId + '\'' +
                ", QOpenId='" + QOpenId + '\'' +
                ", WOpenId='" + WOpenId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", Level='" + Level + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
