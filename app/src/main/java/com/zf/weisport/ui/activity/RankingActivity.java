package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zf.weisport.R;
import com.zf.weisport.adapter.RankingAdapter;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.util.UserUtil;
import com.zf.weisport.model.GetRankModel;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.IRankingCallback;
import com.zf.weisport.ui.viewmodel.RankingViewModel;
import com.zf.widget.recycleview.DividerItemDecoration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @version V1.0 <全国排名>
 * @author: Xs
 * @date: 2016-09-21 15:25
 * @email Xs.lin@foxmail.com
 */
public class RankingActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate,
        IRankingCallback,View.OnClickListener{

    @Bind(R.id.multi_state_view)
    MultiStateView multiStateView;
    @Bind(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ImageView mImgHead;
    private TextView mTvRankValue;
    private TextView mTvRankValueFinal;
    private TextView mTvLeave;

    private RankingViewModel _viewModel;
    private RankingAdapter   mAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,RankingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);
        _viewModel = new RankingViewModel(this);
        initView();

        if (!UserUtil.isLogin(this))
            mTvRankValueFinal.setText(getString(R.string.no_login));
        else
            _viewModel.getMyRank();

        _viewModel.getRankList();
    }

    private void initView() {

        mImgHead = (ImageView)findViewById(R.id.img_rankHead);
        mTvRankValue = (TextView)findViewById(R.id.current_rank_value_id);
        mTvRankValueFinal = (TextView)findViewById(R.id.current_rank_value_id_final);
        mTvLeave = (TextView) findViewById(R.id.ranking_title_leave);
        mTvLeave.setOnClickListener(this);

        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        bgaRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(this, true);
        viewHolder.setLoadingMoreText(getString(R.string.loading_more_text));
        bgaRefreshLayout.setRefreshViewHolder(viewHolder);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new RankingAdapter(this);
        recyclerView.setAdapter(mAdapter);

        if (!TextUtils.isEmpty(User.getUser().getHeadUrl()))
            ImageLoader.getInstance().displayImage(User.getUser().getHeadUrl(),mImgHead);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        _viewModel.getRankList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onGetMyRankSuccess(MyRankModel myRankModel) {
        mTvRankValue.setText(myRankModel.getNum()+"名");
        mTvRankValueFinal.setText(getString(R.string.ranking_record_tv3));
    }

    @Override
    public void onGetRankSuccess(List<GetRankModel> getRankModels) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        onFinishRefreshView();
        mAdapter.setData(getRankModels);
    }

    @Override
    public void onNetError() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        onFinishRefreshView();
    }

    @Override
    public void onNetEmpty() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        onFinishRefreshView();
    }

    @Override
    public void onFinishRefreshView() {
        Observable.just("").delay(300, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            if (bgaRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING)
                bgaRefreshLayout.endRefreshing();
            if (bgaRefreshLayout.isLoadingMore())
                bgaRefreshLayout.endLoadingMore();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ranking_title_leave:
                finish();
                break;
        }
    }
}
