package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.net.RequestFileHelper;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.FileModel;
import com.zf.weisport.presenter.IPersionView;
import com.zf.weisport.presenter.biz.IPersionBiz;

import java.io.File;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-13 14:42
 * @email Xs.lin@foxmail.com
 */
public class PersionBizImpl extends BaseBiz<IPersionView> implements IPersionBiz {

    public PersionBizImpl(IPersionView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void loadInitData() {
        /**
         * initview
         */
        getView().setHeadUrl(User.getUser().getHeadUrl());
        getView().setName(User.getUser().getName());
        getView().setSex(User.getUser().getSex());
        if ("0".equals(getView().getSex())) {
            getView().setFemaleCheck(true);
            getView().setMaleCheck(false);
        } else {
            getView().setFemaleCheck(false);
            getView().setMaleCheck(true);
        }
    }

    @Override
    public void commit(FileModel fileModel) {

        if (getView().getMaleCheck())
            getView().setSex("1");
        else
            getView().setSex("0");

        String HeadImgId = fileModel == null ? "" : fileModel.Img_ID;
        String filePath = fileModel == null ? User.getUser().getHeadUrl() : fileModel.FilePath;
        addSubscription(
                RequestHelper.getInstance().updUser(User.getUser().getId(),
                        HeadImgId,getView().getName(),getView().getSex()).
                        doOnNext(updUserInfoModel -> {
                            if (updUserInfoModel.isSuccess()) {
                                //更新 单例User/数据库User headUrl、HeadUrl、Name、Sex;
                                AppDatabaseCache.getCache(UIUtil.getContext())
                                        .updateUser_HeadUrl_Name_Sex(filePath, getView().getName(), getView().getSex());
                                User.getUser().setUser(AppDatabaseCache.getCache(UIUtil.getContext()).queryUser());
                                getView().onCommitCompleted();
                            }
                        }).subscribe(getSubscriber())
        );

    }

    @Override
    public void commitHeadImg() {

        File file = new File(getView().getFileUri().getPath());
        addSubscription(
            RequestFileHelper.getInstance().upload(file).
                subscribe(new Subscriber<FileModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showToast(com.xs.basic_mvvm.R.string.net_error_toast);
                        dismissLoadingView();
                    }

                    @Override
                    public void onNext(FileModel fileModel) {
                        if (fileModel.isSuccess()) {
                            commit(fileModel.getData().get(0));
                        }
                    }
                })
        );
    }


}
