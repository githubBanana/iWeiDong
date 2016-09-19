package com.zf.weisport.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.zf.weisport.R;
import com.zf.weisport.databinding.ItemTopicCommentListBinding;
import com.zf.weisport.model.TopicCommentModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-19 11:22
 * @email Xs.lin@foxmail.com
 */
public class TopicCommentAdapter extends BaseAdapter<TopicCommentModel> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_comment_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.setData(getItem(position), position);
    }

    private static class Holder extends RecyclerView.ViewHolder {

        ItemTopicCommentListBinding mBinding;

        public Holder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setData(TopicCommentModel model, int position) {
            mBinding.setTopicCommentModel(model);
            Glide.with(mBinding.ivIcon.getContext()).load(model.getHeadImg()).asBitmap().centerCrop().placeholder(R.mipmap.ic_default_head).into(new BitmapImageViewTarget(mBinding.ivIcon) {
                @Override
                protected void setResource(Bitmap resource) {
                    super.setResource(resource);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mBinding.ivIcon.getResources(), resource);
                    roundedBitmapDrawable.setCircular(true);
                    getView().setImageDrawable(roundedBitmapDrawable);
                }
            });
        }
    }

}
