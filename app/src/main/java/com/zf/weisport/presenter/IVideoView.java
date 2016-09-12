package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.GetVideoModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 16:18
 * @email Xs.lin@foxmail.com
 */
public interface IVideoView extends IBaseView {

    /**
     * 获取轮播图完成回调
     * @param getVideoModels
     */
    void onGetVideoCompleted(List<GetVideoModel> getVideoModels);

    /**
     * 访问网络错误
     */
    void onNetErrorNotifyUI();

    /**
     * 访问网络数据空回调
     */
    void onNetEmptyNotifyUI();

    /**
     * 获取视频类型
     */
    int getType();

    /**
     * 设置视频类型
     * @param type
     */
    void setType(int type);

    /**
     * 设置页码
     */
    int getPageIndex();

    /**
     * 设置页码
     * @param pageIndex
     */
    void setPageIndex(int pageIndex);

    /**
     * 设置页总数
     * @param totalCount
     */
    void setTotalCount(int totalCount);

    /**
     * 获取页总数
     * @return
     */
    int getTotalCount();
}
