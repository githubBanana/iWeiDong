package com.zf.weisport.ui.callback;

import com.zf.weisport.model.GetRankModel;
import com.zf.weisport.model.MyRankModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 15:56
 * @email Xs.lin@foxmail.com
 */
public interface IRankingCallback extends IBaseCallback {

    /**
     * 获取我的排名成功
     * @param myRankModel
     */
    void onGetMyRankSuccess(MyRankModel myRankModel);

    /**
     * 获取全国排名列表成功
     * @param getRankModels
     */
    void onGetRankSuccess(List<GetRankModel> getRankModels);
}
