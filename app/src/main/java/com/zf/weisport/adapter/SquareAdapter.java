package com.zf.weisport.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.weisport.R;
import com.zf.weisport.databinding.ItemListSquareBinding;
import com.zf.weisport.model.LabelTopicModel;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2016/9/9.
 */
public class SquareAdapter extends BaseAdapter<LabelTopicModel> implements View.OnClickListener {

    private Activity mAct;
    private OnItemClickListener<LabelTopicModel> mOnItemClickListener;

    public SquareAdapter(Activity activity,OnItemClickListener<LabelTopicModel> listener) {
        this.mAct = activity;
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_square,parent,false);
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
        LabelTopicModel _model;
        int position;
        ItemListSquareBinding mBinding;

        public Holder(View itemView) {
            super(itemView);
            this.mItemView = itemView;
            mBinding = DataBindingUtil.bind(mItemView);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(mItemView);
        }

        public void setData(LabelTopicModel model,int position) {
            this._model = model;
            this.position = position;
            mBinding.setLabelTopicViewModel(model);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
