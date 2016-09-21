package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.GetRankModel;
import com.zf.weisport.model.MyRankModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 15:58
 * @email Xs.lin@foxmail.com
 */
public interface IRankingView extends IBaseView {

    /**
     * 获取我的排名完成
     * @param myRankModel
     */
    void onGetMyRankCompleted(MyRankModel myRankModel);

    /**
     * 获取全国排名列表完成
     * @param getRankModelList
     */
    void onGetRankListCompleted(List<GetRankModel> getRankModelList);

    /**
     * 访问网络错误
     */
    void onNetErrorNotifyUI();

    /**
     * 访问网络数据空回调
     */
    void onNetEmptyNotifyUI();

}
