package com.zf.weisport.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.weisport.R;
import com.zf.weisport.databinding.ItemLabelListBinding;
import com.zf.weisport.model.GetLabelModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-20 13:53
 * @email Xs.lin@foxmail.com
 */
public class LabelShowAdapter extends BaseAdapter<GetLabelModel> implements View.OnClickListener{

    private OnItemClickListener<GetLabelModel> mOnItemClickListener;

    public LabelShowAdapter(OnItemClickListener<GetLabelModel> listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_list,parent,false);
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
        GetLabelModel _model;
        int position;
        ItemLabelListBinding mBinding;

        public Holder(View itemView) {
            super(itemView);
            this.mItemView = itemView;
            mBinding = DataBindingUtil.bind(mItemView);
        }

        public void setData(GetLabelModel model,int position) {
            this._model = model;
            this.position = position;
            mBinding.setGetLabelModel(model);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
