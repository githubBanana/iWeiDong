package com.zf.weisport.manager.db.model;

import android.content.Context;
import android.util.Log;

import com.diy.dblib.util.DatabaseHelper;
import com.diy.dblib.util.GenericDao;
import com.diy.dblib.util.GenericDaoImpl;
import com.zf.weisport.manager.db.bean.BleDevice;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.util.UserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0 <数据库缓存>
 * @author: Xs
 * @date: 2016-03-26 14:49
 * @email Xs.lin@foxmail.com
 */
public class AppDatabaseCache {
    private static final String TAG = "AppDatabaseCache";

    private static AppDatabaseCache instance;
    private Context mContext;

    private DatabaseHelper mDataHelper;
    private GenericDao<User,Integer> mUserGenericDao;
    private GenericDao<BleDevice,Integer> mBleDeviceGenericDao;

    /**
     * 单例获取
     * @param context
     * @return
     */
    public static AppDatabaseCache getCache(Context context) {
        if (instance == null) {
            synchronized (AppDatabaseCache.class) {
                if(instance == null) {
                    instance = new AppDatabaseCache(context);
                }
            }
        }
        return instance;
    }

    private AppDatabaseCache(Context context){
        this.mContext = context;
        mDataHelper = AppDatabaseHelper.getHelper(mContext);
        mUserGenericDao = new GenericDaoImpl(mContext,mDataHelper,User.class);
        mBleDeviceGenericDao = new GenericDaoImpl<>(mContext,mDataHelper,BleDevice.class);
    }

    /*public void test() {
        List<User> userList = mUserGenericDao.queryForAll();
        Log.i(TAG,"user data test size :"+userList.size());
        for (int i = 0;i < userList.size();i++) {
            Log.i(TAG,"user data test::"+userList.get(i).toString());
        }
    }*/

    public User queryUser() {
        List<User> list = mUserGenericDao.queryForAll();
        if (list != null && list.size() != 0) {
            Log.e("showtag","sql当前user-->"+list.get(0).toString());
            return list.get(0);
        }
        else
            return null;
    }
    /*add user :   only one user*/
    public void userAdd(User t) {
        List<User> userList = mUserGenericDao.queryForAll();
        if (userList.size() == 0) {
            Log.i(TAG,"xixiuserList.size() == 0");
            mUserGenericDao.createOrUpdate(t);
        } else {
            Log.i(TAG,"xixiuserList.size() == "+userList.size());
            mUserGenericDao.deleteAll();
            mUserGenericDao.createOrUpdate(t);
        }
    }
    /*delete user*/
    public void deleteUser() {
        mUserGenericDao.deleteAll();
    }

    /*update user phone*/
    public void updateUserPhone(String phone) {
        Map<String,Object> map = new HashMap<>();
        map.put(User.PHONE,phone);
        mUserGenericDao.updateByFieldValues(returnCheckMap(), map);
    }

    /*update user HeadUrl、Img_ID,name、sex*/
    public void updateUser_HeadUrl_Name_Sex(String HeadUrl,String Name,String Sex) {
        Map<String,Object> mapNew = new HashMap<>();
        mapNew.put(User.HEAD_URL, HeadUrl);
        mapNew.put(User.NAME,Name);
        mapNew.put(User.SEX,Sex);
        mUserGenericDao.updateByFieldValues(returnCheckMap(),mapNew);
    }

    public void updateUser_Level(String level) {
        Map<String,Object> mapNew = new HashMap<>();
        mapNew.put(User.LEVLE, level);
        mUserGenericDao.updateByFieldValues(returnCheckMap(),mapNew);
    }


    protected Map<String,Object> returnCheckMap() {
        Map<String,Object> mapCheck = new HashMap<>();
        mapCheck.put(User.NET_ID, User.getUser().getId());
        return mapCheck;
    }

    /**
     *
     * 数据库中的蓝牙数据通过 NET_ID，ADDRESS,TYPE来区分
     *
     */
    /*ble设备信息保存、查询*/
    public void saveBleDevice(String type,String deviceId,String address,String name) {

        Map<String,Object> map = new HashMap<>();
        map.put(BleDevice.NET_ID, getDbUserId());
        map.put(BleDevice.ADDRESS,address);
        map.put(BleDevice.TYPE, type);
        BleDevice bleDevice = mBleDeviceGenericDao.queryForFieldValuesAndFirst(map);
        if (bleDevice == null) {
            BleDevice b = new BleDevice();
            b.setAddress(address);
            b.setName(name);
            b.setNetId(getDbUserId());
            b.setLast_connected(true);
            b.setType(type);
            mBleDeviceGenericDao.createOrUpdate(b);
            return;
        }
        Map<String,Object> map2 = new HashMap<>();
        map2.put(BleDevice.NET_ID, getDbUserId());
        map2.put(BleDevice.TYPE, type);
        List<BleDevice> lists = mBleDeviceGenericDao.queryForFieldValues(map2);
        if (lists != null) {
            for (int i = 0;i < lists.size();i++) {
                Map<String, Object> mapCheck = new HashMap<>();
                mapCheck.put(BleDevice.NET_ID, getDbUserId());
                Map<String, Object> mapModify = new HashMap<>();
                if (lists.get(i).getAddress().equals(address)) {
                    mapCheck.put(BleDevice.ADDRESS, address);
                    mapModify.put(BleDevice.LAST_CONNECTED, true);
                } else {
                    mapCheck.put(BleDevice.ADDRESS, lists.get(i).getAddress());
                    mapModify.put(BleDevice.LAST_CONNECTED, false);
                }
                mapModify.put(BleDevice.NAME, name);
                mBleDeviceGenericDao.updateByFieldValues(mapCheck, mapModify);
            }
        }

        //test
     /*   List<BleDevice> bleDevices1 = new ArrayList<>();
        bleDevices1 = mBleDeviceGenericDao.queryForAll();
        for (int i = 0; i < bleDevices1.size(); i++) {
            Log.e("test","bleDevices1:"+bleDevices1.get(i).toString());
        }*/
    }
    /*获取最近连接过的ble address*/
    public String getLastConnectedAddress() {
        Map<String,Object> map = new HashMap<>();
        map.put(BleDevice.NET_ID,getDbUserId());
        map.put(BleDevice.LAST_CONNECTED, true);

        BleDevice bleDevice = mBleDeviceGenericDao.queryForFieldValuesAndFirst(map);
        Log.i("tes","bleDevicebleDevice:"+bleDevice+" map>"+map.toString());
        if (bleDevice != null)
            return bleDevice.getAddress();
        else
            return null;
    }
    /*获取连接过的ble 设备列表*/
    public List<BleDevice> getBleDeviceList(String type) {
        Map<String,Object> map = new HashMap<>();
        map.put(BleDevice.NET_ID,getDbUserId());
        map.put(BleDevice.TYPE,type);
        List<BleDevice> list = mBleDeviceGenericDao.queryForFieldValues(map);
        for (int i = 0; i < list.size(); i++) {
            Log.i("test","获取连接过的ble 设备列表:"+list.get(i).getAddress());
        }
        return list;
    }
/*未登录状态 默认用户存储ID为字符串0*/
    private String getDbUserId() {
        if (UserUtil.isLogin(mContext))
            return User.getUser().getId();
        else
            return "0";
    }



}
