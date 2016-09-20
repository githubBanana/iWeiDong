package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.AddLabelModel;
import com.zf.weisport.model.GetLabelModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 14:04
 * @email Xs.lin@foxmail.com
 */
public interface ITopicLabelShowView extends IBaseView {

    /**
     * 获取话题标签列表完成
     * @param getLabelModels
     */
    void onGetTopicLabelListCompleted(List<GetLabelModel> getLabelModels);

    /**
     * 新建标签完成
     * @param addLabelModel
     */
    void onNewLabelCompleted(AddLabelModel addLabelModel);
    /**
     * 设置标签输入内容
     * @param labelContent
     */
    void setLabelContent(String labelContent);

    /**
     * 获取输入内容
     * @return
     */
    String getLabelContent();
}
