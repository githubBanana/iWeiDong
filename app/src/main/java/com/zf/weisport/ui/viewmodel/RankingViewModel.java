package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.GetRankModel;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.presenter.IRankingView;
import com.zf.weisport.presenter.biz.IRankingBiz;
import com.zf.weisport.presenter.biz.impl.RankingBizImpl;
import com.zf.weisport.ui.callback.IRankingCallback;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 15:54
 * @email Xs.lin@foxmail.com
 */
public class RankingViewModel extends BaseViewModel<IRankingCallback,MyRankModel> implements IRankingView {

    private IRankingBiz biz;

    public RankingViewModel(IRankingCallback iRankingCallback) {
        super(iRankingCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new RankingBizImpl(this,this);
    }

    /**
     * 获取我的排名
     */
    public void getMyRank() {
        biz.getMyRank();
    }

    /**
     * 获取全国排名列表
     */
    public void getRankList() {
        biz.getRankList();
    }

    @Override
    public void onGetMyRankCompleted(MyRankModel myRankModel) {
        getCallback().onGetMyRankSuccess(myRankModel);
    }

    @Override
    public void onGetRankListCompleted(List<GetRankModel> getRankModelList) {
        getCallback().onGetRankSuccess(getRankModelList);
    }

    @Override
    public void onNetErrorNotifyUI() {
        getCallback().onNetError();
    }

    @Override
    public void onNetEmptyNotifyUI() {
        getCallback().onNetEmpty();
    }
}

