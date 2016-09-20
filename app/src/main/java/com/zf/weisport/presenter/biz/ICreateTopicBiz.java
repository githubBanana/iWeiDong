package com.zf.weisport.presenter.biz;

import java.io.File;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 15:24
 * @email Xs.lin@foxmail.com
 */
public interface ICreateTopicBiz {

    /**
     * 发布
     */
    void release();

    /**
     * 发布话题
     */
    void releaseTopic();

    /**
     * 上传图片
     */
    void uploadFile(File file);
}
