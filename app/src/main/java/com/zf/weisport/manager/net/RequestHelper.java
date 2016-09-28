package com.zf.weisport.manager.net;

import com.xs.basic_mvvm.model.BaseModel;
import com.xs.net.retrofit.RetrofitClient;
import com.zf.weisport.model.AddLabelModel;
import com.zf.weisport.model.AddTopicModel;
import com.zf.weisport.model.DeviceConnectModel;
import com.zf.weisport.model.GetLabelModel;
import com.zf.weisport.model.GetRankModel;
import com.zf.weisport.model.GetTopModel;
import com.zf.weisport.model.GetVideoModel;
import com.zf.weisport.model.LabelTopicModel;
import com.zf.weisport.model.LoginModel;
import com.zf.weisport.model.MyFollowListModel;
import com.zf.weisport.model.MyMessageModel;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.model.RegisterModel;
import com.zf.weisport.model.SMSCodeModel;
import com.zf.weisport.model.StatisticsModel;
import com.zf.weisport.model.SyncInfoModel;
import com.zf.weisport.model.TopicCommentModel;
import com.zf.weisport.model.TopicModel;
import com.zf.weisport.model.UpGameModel;
import com.zf.weisport.model.UpdUserInfoModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class RequestHelper {


    public static final String HOST = "http://192.168.1.222:8080";
    public static final String WEB_SERVICE = "http://wd.zfwsc.com/WebService/WeiYunDong.asmx/";
    public static final String ABOUT_US = "http://weidongzn.com/Web/App/AboutUs";
    public static final int PAGESIZE = 10;

    private static final RequestHelper INSTANCE = new RequestHelper();
    private RequestHelper() {}
    public static final RequestHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Login
     * 登陆
     * LoginName:登陆名(手机号)
     * Password:密码
     * UUID:设备唯一标识
     * AppType:App类型(1-安卓 2-IOS)
     *
     * @param LoginName
     * @param Password
     * @param UUID
     * @param AppType
     * @return
     */
    public Observable<LoginModel> login(String LoginName, String Password, String UUID, int AppType) {
        Map<String, Object> map = new HashMap<>();
        map.put("LoginName", LoginName);
        map.put("Password", Password);
        map.put("UUID", UUID);
        map.put("AppType", String.valueOf(AppType));
        return new RetrofitClient.Builder().method("Login").map(map).post(LoginModel.class);
    }

    /**
     * OpenLogin
     * 第三方登录
     * OpenId:第三方用户唯一标识
     * Type:4-微信 5-QQ 6-微博
     * UUID:设备唯一标识
     * AppType:App类型(1-安卓 2-IOS)
     *
     * @param OpenId
     * @param Type
     * @param UUID
     * @param AppType
     * @return
     */
    public static Observable<LoginModel> openLogin(String OpenId, int Type, String UUID, int AppType) {
        Map<String, Object> map = new HashMap<>();
        map.put("OpenId", OpenId);
        map.put("Type", String.valueOf(Type));
        map.put("UUID", UUID);
        map.put("AppType", String.valueOf(AppType));
        return new RetrofitClient.Builder().method("OpenLogin").map(map).post(LoginModel.class);
    }

    /**
     * SyncInfo
     * 第三方同步资料
     * UserId:用户ID
     * HeadImg:用户头像URL
     * Sex：性别
     * Name:用户昵称
     *
     * @param UserId
     * @param HeadImg
     * @param Sex
     * @param Name
     * @return
     */
    public Observable<SyncInfoModel> SyncInfo(String UserId, String HeadImg, String Sex, String Name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserId", UserId);
        map.put("HeadImg", HeadImg);
        map.put("Sex", Sex);
        map.put("Name", Name);
        return new RetrofitClient.Builder().method("SyncInfo").map(map).post(SyncInfoModel.class);
    }

    /**
     * Register
     * 手机号注册接口
     * Mobile:手机号
     * Password:密码
     *
     * @param Mobile
     * @param Password
     * @return
     */
    public Observable<RegisterModel> register(String Mobile, String Password) {
        Map<String, Object> map = new HashMap<>();
        map.put("Mobile", Mobile);
        map.put("Password", Password);
        return new RetrofitClient.Builder().method("Register").map(map).post(RegisterModel.class);
    }

    /**
     * GetSMSCode
     * 获取短信验证码
     * Mobile:手机号
     * Type:类型 (1:注册 2:找回密码 3:第三方绑定)
     *
     * @param Mobile
     * @param Type
     * @return
     */
    public Observable<SMSCodeModel> getSMSCode(String Mobile, int Type) {
        Map<String, Object> map = new HashMap<>();
        map.put("Mobile", Mobile);
        map.put("Type", String.valueOf(Type));
        return new RetrofitClient.Builder().method("GetSMSCode").map(map).post(SMSCodeModel.class);
    }

    /**
     * GetTop
     * 获取轮播图
     *
     * @return
     */
    public Observable<GetTopModel> getTop() {
        return new RetrofitClient.Builder().method("GetTop").post2(GetTopModel.class);
    }

    /**
     * GetLabelTopic
     * 获取标签朋友圈消息
     *
     * @return
     */
    public Observable<LabelTopicModel> getLabelTopic() {
        return new RetrofitClient.Builder().method("GetLabelTopic").post2(LabelTopicModel.class);
    }

    /**
     * GetVideo
     * 获取视频列表
     * Type:0-推荐视频 1-健身教学 2-创新玩法 3-超神竞技
     * PageIndex:页码
     * PageSize:页数量
     *
     * @param Type
     * @param PageIndex
     * @return
     */
    public static Observable<GetVideoModel> getVideo(int Type, int PageIndex) {
        Map<String, Object> map = new HashMap<>();
        map.put("Type", Type);
        map.put("PageIndex", PageIndex);
        map.put("PageSize", PAGESIZE);
        return new RetrofitClient.Builder().method("GetVideo").map(map).post(GetVideoModel.class);
    }


    /**
     * UpdUser
     * 更新用户资料
     * User_ID:用户ID
     * Head_Img:上传图片的ID
     * Name:昵称
     * Sex:性别(0:女 1:男)
     *
     * @param User_ID
     * @param Head_Img
     * @param Name
     * @param Sex
     * @return
     */
    public Observable<UpdUserInfoModel> updUser(String User_ID, String Head_Img, String Name, String Sex) {
        Map<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Head_Img", Head_Img);
        map.put("Name", Name);
        map.put("Sex", Sex);
        return new RetrofitClient.Builder().method("UpdUser").map(map).post(UpdUserInfoModel.class);
    }

    /**
     * GetStatistics
     * 获取用户统计信息
     * Device_Type:设备类型(1-引力球)
     * User_ID :用户ID
     * Type:1-周 2-月 3-年
     *
     * @param Device_Type
     * @param User_ID
     * @param Type
     * @return
     */
    public Observable<StatisticsModel> getStatistics(String Device_Type, String User_ID, String Type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Device_Type", Device_Type);
        map.put("User_ID", User_ID);
        map.put("Type", Type);
        return new RetrofitClient.Builder().method("GetStatistics").map(map).post(StatisticsModel.class);
    }

    /**
     * MyMessage
     * 我的消息列表
     * User_ID:当前用户ID
     * PageIndex:页码
     * PageSize:页数量
     * @param User_ID
     * @param PageIndex
     * @param PageSize
     * @return
     */
    public Observable<MyMessageModel> getMyMessage(String User_ID, String PageIndex, String PageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("PageIndex", PageIndex);
        map.put("PageSize", PageSize);
        return new RetrofitClient.Builder().method("MyMessage").map(map).post(MyMessageModel.class);
    }

    /**
     * ReadMessage
     * 读取消息
     * User_ID:用户ID
     * Message_ID
     * @param User_ID
     * @param Message_ID
     * @return
     */
    public Observable<BaseModel> requestReadMessage(String User_ID,String Message_ID) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Message_ID", Message_ID);
        return new RetrofitClient.Builder().method("ReadMessage").map(map).post(BaseModel.class);
    }

    /**
     * MyFollowList
     我的关注列表
     User_ID:用户ID
     * @param User_ID
     * @return
     */
    public Observable<MyFollowListModel> getFollowFriendsList(String User_ID) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        return new RetrofitClient.Builder().method("MyFollowList").map(map).post(MyFollowListModel.class);
    }

    /**
     * 收藏 ChangeCollect
     * User_ID:用户ID
     * Topic_ID:
     * Type:1-收藏 0-取消收藏
     *
     * @param User_ID
     * @param Topic_ID
     * @param Type
     * @return
     */
    public static Observable<BaseModel> requestChangeCollect(String User_ID, String Topic_ID, int Type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Topic_ID", Topic_ID);
        map.put("Type", String.valueOf(Type));
        return new RetrofitClient.Builder().method("ChangeCollect").map(map).post(BaseModel.class);
    }

    /**
     *    AddFollow
     *    添加关注
     *  User_ID:用户ID
     *  Friend_ID:好友ID
     *  Type:1-关注 0-取消关注
     * @param User_ID
     * @param Friend_ID
     * @param Type
     * @return
     */
    public static Observable<BaseModel> requestAddFollow(String User_ID, String Friend_ID, int Type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Friend_ID", Friend_ID);
        map.put("Type", String.valueOf(Type));
        return new RetrofitClient.Builder().method("AddFollow").map(map).post(BaseModel.class);
    }



    /**
     *    GetTopic
     *            分页获取话题列表
     * Type:请求类型（1-标签 2-收藏 3-用户发布）
     * Obj_ID:对应Type需要的ID
     * User_ID:当前用户ID
     * PageIndex:页码
     * PageSize:页数量
     *
     * @param Type
     * @param Obj_ID
     * @param User_ID
     * @param PageIndex
     * @param PageSize
     * @return
     */
    public Observable<TopicModel> requestGetTopic(int Type, String Obj_ID, String User_ID, int PageIndex, int PageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Type", Type + "");
        map.put("Obj_ID", Obj_ID);
        map.put("PageIndex", PageIndex + "");
        map.put("PageSize", PageSize + "");
        return new RetrofitClient.Builder().method("GetTopic").map(map).post(TopicModel.class);
    }

    /**
     * GetCommentList
     * 获取评论列表
     * Topic_ID:话题ID
     * PageIndex:页码
     * PageSize:页数量
     *
     * @param Topic_ID
     * @param PageIndex
     * @param PageSize
     * @return
     */
    public Observable<TopicCommentModel> requestGetCommentList(String Topic_ID, int PageIndex, int PageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Topic_ID", Topic_ID);
        map.put("PageIndex", String.valueOf(PageIndex));
        map.put("PageSize", String.valueOf(PageSize));
        return new RetrofitClient.Builder().method("GetCommentList").map(map).post(TopicCommentModel.class);
    }

    /**
     * AddComment
     * 添加评论
     * User_ID:用户ID
     * Topic_ID:话题ID
     * Content:评论内容
     *
     * @param User_ID
     * @param Topic_ID
     * @param Content
     * @return
     */
    public Observable<BaseModel> requestAddComment(String User_ID, String Topic_ID, String Content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Topic_ID", Topic_ID);
        map.put("Content", Content);
        return new RetrofitClient.Builder().method("AddComment").map(map).post(BaseModel.class);
    }

    /**
     * GetLabel
     * 获取话题标签
     * User_ID:用户ID
     *
     * @param User_ID
     * @return
     */
    public Observable<GetLabelModel> getlabel(String User_ID) {
        Map<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        return new RetrofitClient.Builder().method("GetLabel").map(map).post(GetLabelModel.class);
    }


    /**
     * AddLabel
     * 添加话题标签
     * User_ID:用户ID
     * Name:名称
     *
     * @param User_ID
     * @param Name
     * @return
     */
    public Observable<AddLabelModel> newLabel(String User_ID, String Name) {
        Map<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Name", Name);
        return new RetrofitClient.Builder().method("AddLabel").map(map).post(AddLabelModel.class);
    }

    /**
     * AddTopic
     * 添加话题
     * User_ID:用户ID
     * Content:内容
     * Imgs:图片ID集合,分割
     * Labels:话题ID集合,分割
     *
     * @param User_ID
     * @param Content
     * @param Imgs
     * @param Labels
     * @return
     */
    public static Observable<AddTopicModel> addTopic(String User_ID, String Content, String Imgs, String Labels) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Content", Content);
        map.put("Imgs", Imgs);
        map.put("Labels", Labels);
        return new RetrofitClient.Builder().method("AddTopic").map(map).post(AddTopicModel.class);
    }

    /**
     * UpdPassword
     * 修改密码
     * Mobile:手机号
     * Password:密码
     * OldPassword：旧密码
     * @param Mobile
     * @param Password
     * @return
     */
    public Observable<BaseModel> updPassword(String Mobile, String Password,String OldPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("Mobile", Mobile);
        map.put("Password", Password);
        map.put("OldPassword", OldPassword);
        return new RetrofitClient.Builder().method("UpdPassword").map(map).post(BaseModel.class);
    }

    /**
     * BindAccount
     * 绑定手机号
     * User_ID:用户ID
     * Mobile:绑定手机
     * Password:用户密码
     * @param User_ID
     * @param Mobile
     * @param Password
     * @return
     */
    public Observable<BaseModel> requestBindAccount(String User_ID,String Mobile,String Password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Mobile", Mobile);
        map.put("Password", Password);
        return new RetrofitClient.Builder().method("BindAccount").map(map).post(BaseModel.class);
    }


    /**
     * GetMyRank
     * 获取用户排名
     * User_ID:用户ID
     *
     * @param User_ID
     * @return
     */
    public Observable<MyRankModel> requestGetMyRank(String User_ID) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        return new RetrofitClient.Builder().method("GetMyRank").map(map).post(MyRankModel.class);
    }

    /**
     * GetRank
     * 分页获取排名榜单
     * PageIndex:页码
     * PageSize:页数量
     *
     * @param PageIndex
     * @return
     */
    public Observable<GetRankModel> requestGetRank(String PageIndex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("PageIndex", PageIndex);
        map.put("PageSize", "100");
        return new RetrofitClient.Builder().method("GetRank").map(map).post(GetRankModel.class);
    }

    /**
     * DeviceConnect
     * 设备连接记录接口
     * User_ID:用户ID(手机号)
     * MAC:设备MAC
     * @param User_ID
     * @param MAC
     * @return
     */
    public static Observable<DeviceConnectModel> deviceConnect(String User_ID,String MAC) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("User_ID",User_ID);
        map.put("MAC",MAC);
        return new RetrofitClient.Builder().method("DeviceConnect").map(map).post(DeviceConnectModel.class);
    }

    /**UpGameArr
     上传历史记录
     Data:记录数组{"Data"：[{"User_ID":1,"Device_ID":1,...},{}]}
     记录对象属性值：
     User_ID:用户ID
     Device_ID:设备ID
     Calorie:消耗卡路里
     Device_Type:设备类型
     Start_Time:开始时间(时间戳格式)
     Long_Time:时长
     Speed:速度*/
    public static Observable<BaseModel> upGameArr(List<HashMap<String,Object>> list) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("Data",list);
        return new RetrofitClient.Builder().method("UpGameArr").map(map).post(BaseModel.class);
    }

    /**
     * UpGame
     * 游戏记录上传接口
     * User_ID:用户ID
     * Device_ID:设备ID
     * Calorie:消耗卡路里
     * Device_Type:设备类型
     * Start_Time:开始时间(时间戳格式)
     * Long_Time:时长
     * Speed:速度
     * @param User_ID
     * @param Device_ID
     * @param Calorie
     * @param Device_Type
     * @param Start_Time
     * @param Long_Time
     * @param Speed
     * @return
     */
    public Observable<UpGameModel> UpGame(String User_ID,String Device_ID,String Calorie,
                                                 String Device_Type,String Start_Time,String Long_Time,String Speed) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("User_ID",User_ID);
        map.put("Device_ID",Device_ID);
        map.put("Calorie",Calorie);
        map.put("Device_Type",Device_Type);
        map.put("Start_Time",Start_Time);
        map.put("Long_Time",Long_Time);
        map.put("Speed",Speed);
        return new RetrofitClient.Builder().method("UpGame").map(map).post(UpGameModel.class);
    }
}

