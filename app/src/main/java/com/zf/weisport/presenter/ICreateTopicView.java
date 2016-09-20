package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.manager.event.Event;
import com.zf.weisport.model.AddTopicModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 15:23
 * @email Xs.lin@foxmail.com
 */
public interface ICreateTopicView extends IBaseView {

    /**
     * 发布话题完成
     * @param addTopicModel
     */
    void onReleaseCompleted(AddTopicModel addTopicModel);

    /**
     * 设置话题标签列表
     * @param labelList
     */
    void setLabelList(List<Event> labelList);

    List<Event> getLabelList();

    /**
     * 设置话题内容
     * @param topicContent
     */
    void setTopicContent(String topicContent);

    String getTopicContent();

    /**
     * 设置图片上传后的图片ID集合
     * @param img_iDs
     */
    void setImg_IDs(List<String> img_iDs);

    List<String> getImg_IDs();

    /**
     * 添加图片ID
     * @param ID
     */
    void addImg_ID(int position ,String ID);

    /**
     * 设置本地图片路径集合
     * @param filePaths
     */
    void setFilePaths(List<String> filePaths);

    List<String> getFilePaths();

}
