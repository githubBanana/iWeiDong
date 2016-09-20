package com.zf.weisport.ui.callback;

import com.zf.weisport.model.AddLabelModel;
import com.zf.weisport.model.GetLabelModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 14:03
 * @email Xs.lin@foxmail.com
 */
public interface ITopicLabelShowCallback extends IBaseCallback {

    /**
     * 获取话题标签列表成功
     * @param getLabelModels
     */
    void onGetTopicLabelListSuccess(List<GetLabelModel> getLabelModels);

    /**
     * 新建标签成功
     * @param addLabelModel
     */
    void onNewLabelSuccess(AddLabelModel addLabelModel);
}
