package com.zf.weisport.presenter;

import android.net.Uri;

import com.xs.basic_mvvm.presenter.IBaseView;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-13 14:45
 * @email Xs.lin@foxmail.com
 */
public interface IPersionView extends IBaseView{

    /**
     * 提交信息完成
     */
    void onCommitCompleted();
    /**
     * 设置头像url
     * @param headUrl
     */
    void setHeadUrl(String headUrl);

    /**
     * 获取头像url
     * @return
     */
    String getHeadUrl();

    /**
     * 设置用户
     * @param name
     */
    void setName(String name);

    /**
     * 获取用户名字
     * @return
     */
    String getName();

    /**
     * 设置用户性别
     * @param sex
     */
    void setSex(String sex);

    /**
     * 获取用户性别
     * @return
     */
    String getSex();

    /**
     * 设置女性check
     * @param check
     */
    void setFemaleCheck(boolean check);

    boolean getFemaleCheck();

    /**
     * 设置男性check
     * @param check
     */
    void setMaleCheck(boolean check);

    boolean getMaleCheck();

    /**
     * 设置头像文件uri
     * @param uri
     */
    void setFileUri(Uri uri);

    /**
     * 获取头像文件uri
     * @return
     */
    Uri getFileUri();



}
