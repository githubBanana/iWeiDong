package com.zf.weisport.presenter.biz.impl;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.xs.basic_mvvm.ui.callback.ICallBck;
import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.PinyinComparator;
import com.zf.weisport.model.MyFollowListModel;
import com.zf.weisport.presenter.IFollowFriendsView;
import com.zf.weisport.presenter.biz.IFollowFriendsBiz;
import com.zf.widget.sortlistview.CharacterParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version V1.0 <已关注好友 业务层>
 * @author: Xs
 * @date: 2016-09-18 15:15
 * @email Xs.lin@foxmail.com
 */
public class FollowFriendsBizImpl extends BaseBiz<IFollowFriendsView> implements IFollowFriendsBiz{

    private CharacterParser     characterParser;
    private PinyinComparator    pinyinComparator;
    public FollowFriendsBizImpl(IFollowFriendsView view, ICallBck callBck) {
        super(view, callBck);
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
    }

    @Override
    public void getFollowFriends() {
        Subscription subscription = RequestHelper.getInstance().getFollowFriendsList(User.getUser().getId())
                .observeOn(Schedulers.io())
                .map(myFollowListModel -> {
                    if (myFollowListModel.isSuccess()) {
                        if (myFollowListModel.isEmptyData())
                            return null;
                        else {
                            List<MyFollowListModel> myFollowListModels = filledData(myFollowListModel.getData());
                            // 根据a-z进行排序源数据
                            Collections.sort(myFollowListModels, pinyinComparator);
                            return myFollowListModels;
                        }
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MyFollowListModel>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onNetErrorNotifyUI();
                        showToast(R.string.net_error_toast);
                    }

                    @Override
                    public void onNext(List<MyFollowListModel> followListModels) {
                        if (followListModels == null)
                            getView().onNetEmptyNotifyUI();
                        else {
                            getView().onGetFollowFriendsCompleted(followListModels);
                        }
                    }
                });
        addSubscription(subscription);
    }

    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<MyFollowListModel> filledData(List<MyFollowListModel> date) {
        List<MyFollowListModel> mSortList = new ArrayList<>();
        for (MyFollowListModel aDate : date) {
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(aDate.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                aDate.setSortLetters(sortString.toUpperCase());
            } else {
                aDate.setSortLetters("#");
            }
            mSortList.add(aDate);
        }
        return mSortList;
    }
}
