package com.zf.weisport.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.weisport.R;
import com.zf.weisport.databinding.ItemListMynewsBinding;
import com.zf.weisport.model.MyMessageModel;

/**
 * Created by Administrator on 2016/9/9.
 */
public class MyNewsAdapter extends BaseAdapter<MyMessageModel> implements View.OnClickListener {

    private Activity mAct;
    private OnItemClickListener<MyMessageModel> mOnItemClickListener;

    public MyNewsAdapter(Activity activity, OnItemClickListener<MyMessageModel> listener) {
        this.mAct = activity;
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mynews,parent,false);
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
        MyMessageModel _model;
        int position;
        ItemListMynewsBinding mBinding;

        public Holder(View itemView) {
            super(itemView);
            this.mItemView = itemView;
            mBinding = DataBindingUtil.bind(mItemView);
        }

        public void setData(MyMessageModel model,int position) {
            this._model = model;
            this.position = position;
            mBinding.setMyMessageModel(model);
            if ("0".equals(_model.getHadRead()))//1表示已经看过
                mBinding.imgPic.setEnabled(false);
            else
                mBinding.imgPic.setEnabled(true);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
