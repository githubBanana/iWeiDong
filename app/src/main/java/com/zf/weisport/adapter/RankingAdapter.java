package com.zf.weisport.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.zf.weisport.R;
import com.zf.weisport.databinding.ItemRankListBinding;
import com.zf.weisport.model.GetRankModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 16:52
 * @email Xs.lin@foxmail.com
 */
public class RankingAdapter extends BaseAdapter<GetRankModel> implements View.OnClickListener{

    public Activity mAct;

    public RankingAdapter(Activity activity) {
        this.mAct = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.mItemView.setTag(h);
        h.setData(getItem(position),position);
        h.mItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mItemView;
        GetRankModel _model;
        int position;
        ItemRankListBinding mBinding;

        public Holder(View itemView) {
            super(itemView);
            this.mItemView = itemView;
            mBinding = DataBindingUtil.bind(mItemView);
        }

        public void setData(GetRankModel model,int position) {
            String speed = model.getSpeed()+"转";
            model.setSpeed(speed);
            this._model = model;
            this.position = position;

            mBinding.setGetRankModel(model);
            mBinding.itemSpeedRankId.setText(_model.getSpeed()+"转");
            Glide.with(mBinding.imgHead.getContext()).load(model.getFilePath()).asBitmap().thumbnail(0.6f).
                    centerCrop().placeholder(R.mipmap.me_false).into(new BitmapImageViewTarget(mBinding.imgHead) {
                @Override
                protected void setResource(Bitmap resource) {
                    super.setResource(resource);
                }
            });
            int imgId = 0;
            switch (position) {
                case 0:
                    imgId = R.mipmap.numberbg1;
                    break;
                case 1:
                    imgId = R.mipmap.numberbg2;
                    break;
                case 2:
                    imgId = R.mipmap.numberbg3;
                    break;
            }
            if (position < 3) {
                mBinding.imgTubiao.setVisibility(View.VISIBLE);
                mBinding.imgTubiao.setImageResource(imgId);
            } else {
                mBinding.imgTubiao.setVisibility(View.INVISIBLE);
            }

        }
        @Override
        public void onClick(View view) {

        }
    }
}
