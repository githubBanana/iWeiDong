package com.zf.weisport.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zf.weisport.R;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.util.GlideUtil;
import com.zf.weisport.manager.util.UserUtil;
import com.zf.weisport.ui.activity.AccountActivity;
import com.zf.weisport.ui.activity.EquipsActivity;
import com.zf.weisport.ui.activity.FollowActivity;
import com.zf.weisport.ui.activity.HistoryRecordActivity;
import com.zf.weisport.ui.activity.MyNewsActivity;
import com.zf.weisport.ui.activity.PersionActivity;
import com.zf.weisport.ui.fragment.base.ToolbarBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  @version V1.0 <我的 界面>
 * Created by Xs on 2016/9/11.
 */
public class MeFragment extends ToolbarBaseFragment{

    @Bind(R.id.headId)
    ImageView mHead;
    @Bind(R.id.head_nameId)
    TextView  mTvName;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getToolbar().setTitle(R.string.mainTab_me);
        getToolbar().setOnMenuItemClickListener(this);
        if (UserUtil.isLogin(getActivity())) {
            //初始化头像
            GlideUtil.showHead(User.getUser().getHeadUrl(),mHead);
            mTvName.setText(User.getUser().getName());
        } else {
            GlideUtil.showHead("",mHead);
            mTvName.setText(R.string.no_login);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.me_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onMenuItemClick(item);
    }

    @OnClick({
            R.id.tv_mine_item1,
            R.id.tv_mine_item2,
            R.id.tv_mine_item3,
            R.id.tv_mine_item4,
            R.id.tv_mine_item5,
            R.id.tv_mine_item6,
            R.id.tv_mine_item7,
            R.id.headId
    })
    public void meOnclick(View view) {
        if (!UserUtil.isLogin(getActivity()))
            AccountActivity.start(getActivity());
        else {
            switch (view.getId()) {
                case R.id.tv_mine_item1:
                    PersionActivity.start(getActivity());
                    break;
                case R.id.tv_mine_item2:
                    HistoryRecordActivity.start(getActivity());
                    break;
                case R.id.tv_mine_item3:
                    break;
                case R.id.tv_mine_item4:
                    MyNewsActivity.start(getActivity());
                    break;
                case R.id.tv_mine_item5:

                    break;
                case R.id.tv_mine_item6:
                    FollowActivity.start(getActivity());
                    break;
                case R.id.tv_mine_item7:
                    EquipsActivity.start(getActivity());
                    break;
                case R.id.headId:
                    if (!UserUtil.isLogin(getActivity()))
                        AccountActivity.start(getActivity());
                    break;
            }
        }
    }

}
