package com.zf.weisport.ui.callback;

import com.diy.labelview.Tag;
import com.zf.weisport.model.AddTopicModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 11:46
 * @email Xs.lin@foxmail.com
 */
public interface ICreateTopicCallback extends IBaseCallback {

    /**
     * 添加话题标签
     * @param mTags
     */
    void addLabelListSuccess(List<Tag> mTags);

    /**
     * 发布话题成功
     * @param addTopicModel
     */
    void onReleaseSuccess(AddTopicModel addTopicModel);
}
