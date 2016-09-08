package com.diy.diylibrary.sharesdk;

import android.content.Context;

import com.diy.diylibrary.sharesdk.onekeyshare.OnekeyShare;

import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by siushen on 2016/3/17.
 */
public class SharePresenter {
    private Context mContext;
    private static SharePresenter instance = null;
    private SharePresenter(Context mContext) {
        this.mContext = mContext;
    }
    public static SharePresenter getPresenter(Context mContext) {
        if(instance == null) {
            synchronized (SharePresenter.class){
                if(instance == null) {
                    instance = new SharePresenter(mContext);
                }
            }
        }
        return instance;
    }

    private  void setInformation(Platform.ShareParams sp) {
        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://www.baidu.com"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");//分享网络图片
    }
    //指定平台分享
    public void showShareAppointPlatform(PlatformActionListener platformActionListener,String platformName,Context context) {
        if (platformName.equals(SinaWeibo.NAME)) {
            ShareSDK.initSDK(context);
            SinaWeibo.ShareParams spWeibo = new SinaWeibo.ShareParams();
            setInformation(spWeibo);
            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
            weibo.setPlatformActionListener(platformActionListener); // 设置分享事件回调
            weibo.share(spWeibo);
        }
        if (platformName.equals(Wechat.NAME)) {
            Wechat.ShareParams spWeixin = new Wechat.ShareParams();
            setInformation(spWeixin);
            Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
            weixin.setPlatformActionListener(platformActionListener);
            weixin.share(spWeixin);
        }
        if (platformName.equals(WechatMoments.NAME)) {
            WechatMoments.ShareParams weixinMoment = new WechatMoments.ShareParams();
            setInformation(weixinMoment);
            Platform moments = ShareSDK.getPlatform(WechatMoments.NAME);
            moments.setPlatformActionListener(platformActionListener);
            moments.share(weixinMoment);
        }
        if (platformName.equals(QQ.NAME)) {
            QQ.ShareParams sp = new QQ.ShareParams();
            setInformation(sp);
            Platform qq = ShareSDK.getPlatform(QQ.NAME);
            qq.setPlatformActionListener(platformActionListener);
            qq.share(sp);
        }

    }
    //一键分享
    public void showShareOnekey(String platform,Map<String,String> map,boolean silent,Context context) {
        ShareSDK.initSDK(context);
       final OnekeyShare oks = new OnekeyShare();
        if (platform.equals(WechatMoments.NAME)) {
            oks.setTitle(map.get(ShareParameters.TITLE)+"\n"+map.get(ShareParameters.TEXT_CONTENT));
        } else {
            oks.setTitle(map.get(ShareParameters.TITLE));
            oks.setText(map.get(ShareParameters.TEXT_CONTENT));
        }
        if (platform.equals(SinaWeibo.NAME)) {
            oks.setText(map.get(ShareParameters.TEXT_CONTENT)+map.get(ShareParameters.URL)+"\n");
        } else {
            oks.setUrl(map.get(ShareParameters.URL));//微博不要调用setUrl 避免imageUrl显示不了
        }
        oks.setTitleUrl(map.get(ShareParameters.URL));
        oks.setImageUrl(map.get(ShareParameters.IMAGE_URL));
        oks.setSilent(silent);
        // 令编辑页面显示为Dialog模式
//        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // 去自定义不同平台的字段内容
//        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

        oks.show(context);
    }

}
