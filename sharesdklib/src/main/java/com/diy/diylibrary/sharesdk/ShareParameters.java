package com.diy.diylibrary.sharesdk;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * for sharesdk
 * Created by siushen on 2016/3/17.
 */
public class ShareParameters {
    public static final String SHARE_WEIXIN = Wechat.NAME;
    public static final String SHARE_WEIXIN_MOMENT = WechatMoments.NAME;
    public static final String SHARE_WEIBO = SinaWeibo.NAME;
    public static final String SHARE_QQ = QQ.NAME;

    public static final int SHUTDOWN_BUTTOM = 1;
    public static final int SHUTDOWN_TOP = 2;
    public static final int SHARE_SUCCESS = 7;
    public static final int SHARE_FAIL = 8;
    public static final int SHARE_CANCLE = 9;

    public static final String TEXT_CONTENT = "text_content";
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String IMAGE_URL = "image_url";
}
