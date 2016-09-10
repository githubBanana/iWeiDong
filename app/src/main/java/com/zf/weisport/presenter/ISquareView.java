package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.GetTopModel;
import com.zf.weisport.model.LabelTopicModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 09:38
 * @email Xs.lin@foxmail.com
 */
public interface ISquareView extends IBaseView{
    /**
     * 获取话题标签完成回调
     * @param labelTopicModels
     */
    void onGetLabelTopicCompleted(List<LabelTopicModel> labelTopicModels);

    /**
     * 获取轮播图完成回调
     * @param getTopModels
     */
    void onGetTopCompleted(List<GetTopModel> getTopModels);

    /**
     * 访问网络错误
     */
    void onNetErrorNotifyUI();

    /**
     * 访问网络数据空回调
     */
    void onNetEmptyNotifyUI();
}
