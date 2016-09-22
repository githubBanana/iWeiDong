package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.db.model.AppDatabaseCache;
import com.zf.weisport.manager.util.UIUtil;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.presenter.IBattleView;
import com.zf.weisport.presenter.biz.IBattleBiz;
import com.zf.weisport.presenter.biz.impl.BattleBizImpl;
import com.zf.weisport.ui.callback.IBattleCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-22 16:47
 * @email Xs.lin@foxmail.com
 */
public class BattleViewModel extends BaseViewModel<IBattleCallback,MyRankModel> implements IBattleView {


    private IBattleBiz biz;
    public BattleViewModel(IBattleCallback iBattleCallback) {
        super(iBattleCallback);
        biz = getBiz();
    }

    @Override
    protected BaseBiz createBiz() {
        return new BattleBizImpl(this,this);
    }

    /**
     * 获取我的排名
     */
    public void getMyRank() {
        biz.getMyRank();
    }

    @Override
    public void onGetMyRankCompleted(MyRankModel myRankModel) {
        //update user and sql
        String levelString = myRankModel.getLevelId();
        User.getUser().setLevel(levelString);
        AppDatabaseCache.getCache(UIUtil.getContext()).updateUser_Level(levelString);

        sortDangrad(levelString);
    }

    /**
     * 将对应的排名名称+排名级别取出
     * @param levelString
     */
    private void sortDangrad(String levelString) {
        final int level = Integer.valueOf(levelString);
        int speedLevel = 0;
        String levelSpecial = "";
        switch (level) {
            case 37:
                levelSpecial = "最强王者";
                speedLevel = 14000;
                break;
            case 36:
                levelSpecial = "至尊钻石";
                speedLevel = 12000;
                break;
            case 35:
                levelSpecial = "荣耀黄金";
                speedLevel = 9000;
                break;
            case 34:
                levelSpecial = "不屈白银";
                speedLevel = 7000;
                break;
            case 45:
                levelSpecial = "英勇黄铜";
                speedLevel = 5000;
                break;
            default:
                levelSpecial = "未知等级";
                speedLevel = 0;
                break;
        }
        getCallback().onGetMyRankSuccess(levelSpecial,speedLevel);
    }
}
