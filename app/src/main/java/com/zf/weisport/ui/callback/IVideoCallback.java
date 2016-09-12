package com.zf.weisport.ui.callback;

import com.zf.weisport.model.GetVideoModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 16:15
 * @email Xs.lin@foxmail.com
 */
public interface IVideoCallback extends IBaseCallback{

    /**
     * 获取video信息成功
     */
    void onGetVideoSuccess();

    /**
     * 获取更多视频信息状态回调
     * status = true ，则表示获取成功，反之失败（包含获取到的数据为null）
     * @param status
     */
    void onGetMoreVideoStatus(boolean status,List<GetVideoModel> getVideoModels);


}
