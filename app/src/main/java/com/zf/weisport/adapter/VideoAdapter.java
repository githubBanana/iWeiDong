package com.zf.weisport.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zf.weisport.R;
import com.zf.weisport.databinding.ItemListVideoBinding;
import com.zf.weisport.model.GetVideoModel;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 15:50
 * @email Xs.lin@foxmail.com
 */
public class VideoAdapter extends BaseAdapter<GetVideoModel> implements View.OnClickListener{

    private Activity mAct;
    private OnItemClickListener<GetVideoModel> mOnItemClickListener;

    public VideoAdapter(Activity activity,OnItemClickListener<GetVideoModel> listener) {
        this.mAct = activity;
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_video,parent,false);
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
        Holder holder = (Holder) view.getTag();
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(view,holder._model,holder.position);
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mItemView;
        GetVideoModel _model;
        int position;
        ItemListVideoBinding mBinding;

        public Holder(View itemView) {
            super(itemView);
            this.mItemView = itemView;
            mBinding = DataBindingUtil.bind(mItemView);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(mItemView);
        }

        public void setData(GetVideoModel model,int position) {
            this._model = model;
            this.position = position;
            mBinding.setGetVideoViewModel(model);
            mBinding.tvTitle.setText(_model.Title);
            mBinding.tvTime.setText(_model.AddTime);
            ImageLoader.getInstance().displayImage(_model.LogoUrl,mBinding.videoIconBg);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
