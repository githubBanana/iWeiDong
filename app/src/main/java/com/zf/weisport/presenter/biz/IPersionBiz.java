package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;
import com.zf.weisport.model.FileModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-13 14:40
 * @email Xs.lin@foxmail.com
 */
public interface IPersionBiz extends IBaseBiz {

    /**
     * 提交个人资料
     */
    void commit(FileModel fileModel);

    /**
     * 上传头像文件
     */
    void commitHeadImg();
}
