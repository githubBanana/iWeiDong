package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.MyRankModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-22 16:50
 * @email Xs.lin@foxmail.com
 */
public interface IBattleView extends IBaseView{

    /**
     * 获取我的排名完成
     * @param myRankModel
     */
    void onGetMyRankCompleted(MyRankModel myRankModel);
}
