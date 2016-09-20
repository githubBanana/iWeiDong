package com.zf.weisport.presenter.biz.impl;

import android.text.TextUtils;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.event.Event;
import com.zf.weisport.manager.net.RequestFileHelper;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.BmCutAndCompress;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.FileModel;
import com.zf.weisport.presenter.ICreateTopicView;
import com.zf.weisport.presenter.biz.ICreateTopicBiz;

import java.io.File;

import rx.Subscriber;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 15:29
 * @email Xs.lin@foxmail.com
 */
public class CreateTopicBizImpl extends BaseBiz<ICreateTopicView> implements ICreateTopicBiz {

    private int uploadFileCount = 0;
    public CreateTopicBizImpl(ICreateTopicView view, ICallBck callBck) {
        super(view, callBck);
    }

    @Override
    public void release() {

        if (TextUtils.isEmpty(getView().getTopicContent())) {
            showToast(R.string.please_input_content);
            return;
        }

        if (getView().getLabelList().size() <= 1) {
            showToast(R.string.please_add_topic_label);
            return;
        }
        showLoadingView();
        uploadFileCount = 0;
        if (getView().getFilePaths().size() != 0) {
            sortUploadFile(getView().getFilePaths().get(uploadFileCount));
        } else {
            releaseTopic();
        }
    }

    @Override
    public void releaseTopic() {

        String content = getView().getTopicContent();
        String labels= getView().getLabelList().get(1).getAddLabelModel().ID;
        for (int i = 2; i < getView().getLabelList().size(); i++) {
            Event event = getView().getLabelList().get(i);
            labels+= ","+event.getAddLabelModel().ID;
        }
        String imgIds = "";
        if (getView().getImg_IDs().size() != 0) {
            imgIds = getView().getImg_IDs().get(0);
            for (int i = 1; i < getView().getImg_IDs().size(); i++) {
                imgIds = imgIds + "," + getView().getImg_IDs().get(i);
            }
        }

        addSubscription(
                RequestHelper.getInstance().addTopic(User.getUser().getId(),content,imgIds,labels).
                        doOnNext(addTopicModel -> {
                            if (addTopicModel.isSuccess()) {
                                getView().onReleaseCompleted(null);
                                showToast(R.string.text_release_success);
                            } else {
                                showToast(addTopicModel.getErrMsg());
                            }
                        }).subscribe(getSubscriber())
        );
    }

    @Override
    public void uploadFile(File file) {
        addSubscription(
                RequestFileHelper.getInstance().upload(file).
                        subscribe(new Subscriber<FileModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                showToast(R.string.net_error_toast);
                                dismissLoadingView();
                            }

                            @Override
                            public void onNext(FileModel fileModel) {
                                if (fileModel.isSuccess()) {
                                    getView().addImg_ID(uploadFileCount,fileModel.getData().get(0).Img_ID);
                                    uploadFileCount++;
                                    if (uploadFileCount <= getView().getFilePaths().size() - 1) {
                                        sortUploadFile(getView().getFilePaths().get(uploadFileCount));
                                    } else {
                                        releaseTopic();
                                    }
                                } else {

                                }
                            }
                        })
        );
    }

    /**
     * 图片处理
     * @param filePath
     */
    private void sortUploadFile(String filePath) {

        new BmCutAndCompress().justDo(filePath, new BmCutAndCompress.IBmCutCompressCallBack() {
            @Override
            public void onSuccess(File file) {
                uploadFile(file);
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        }, UIUtil.getContext());
    }
}
