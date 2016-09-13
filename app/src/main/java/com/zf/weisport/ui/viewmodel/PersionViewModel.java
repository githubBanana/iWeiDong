package com.zf.weisport.ui.viewmodel;

import android.net.Uri;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.FileModel;
import com.zf.weisport.model.UpdUserInfoModel;
import com.zf.weisport.presenter.IPersionView;
import com.zf.weisport.presenter.biz.IPersionBiz;
import com.zf.weisport.presenter.biz.impl.PersionBizImpl;
import com.zf.weisport.ui.callback.IPersionCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-13 14:10
 * @email Xs.lin@foxmail.com
 */
public class PersionViewModel  extends BaseViewModel<IPersionCallback,UpdUserInfoModel> implements IPersionView{

    public String       headUrl;
    public String       name;
    public String       sex;
    public boolean      femaleCheck;
    public boolean      maleCheck;
    public Uri          fileUri;

    private IPersionBiz biz;
    public PersionViewModel(IPersionCallback iPersionCallback) {
        super(iPersionCallback);
        biz = getBiz();
        biz.loadInitData();
    }

    /**
     * 上传头像文件
     */
    public void commitHeadImg() {
        biz.commitHeadImg();
    }

    /**
     * 提交用户信息
     */
    public void commit(FileModel fileModel) {
        biz.commit(fileModel);
    }
    @Override
    protected BaseBiz createBiz() {
        return new PersionBizImpl(this,this);
    }

    @Override
    public void onCommitCompleted() {
        getCallback().onCommitSuccess();
    }

    @Override
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public String getHeadUrl() {
        return this.headUrl;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String getSex() {
        return this.sex;
    }

    @Override
    public void setFemaleCheck(boolean check) {
        this.femaleCheck = check;
    }

    @Override
    public boolean getFemaleCheck() {
        return this.femaleCheck;
    }

    @Override
    public void setMaleCheck(boolean check) {
        this.maleCheck = check;
    }

    @Override
    public boolean getMaleCheck() {
        return this.maleCheck;
    }

    @Override
    public void setFileUri(Uri uri) {
        this.fileUri = uri;
    }

    @Override
    public Uri getFileUri() {
        return this.fileUri;
    }
}
