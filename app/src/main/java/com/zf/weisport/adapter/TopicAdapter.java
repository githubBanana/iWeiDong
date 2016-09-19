package com.zf.weisport.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cy.src.photos.images.ImagesAdapter;
import com.diy.diylibrary.widget.dialog.SheetDialog;
import com.xs.basic_mvvm.model.BaseModel;
import com.xs.net.retrofit.MySubscriber;
import com.zf.weisport.R;
import com.zf.weisport.databinding.ItemTopicBinding;
import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.TimeUtil;
import com.zf.weisport.model.TopicModel;
import com.zf.weisport.ui.activity.TopicCommentActivity;
import com.zf.widget.LoadDialog;

import rx.Observable;
import rx.Subscription;

/**
 * Created by CY on 2016/4/12.
 */
public class TopicAdapter extends BaseAdapter<TopicModel> implements View.OnClickListener {

    private Activity mAct;
    private OnItemClickListener<TopicModel> mOnItemClickListener;

    public TopicAdapter(Activity activity, OnItemClickListener<TopicModel> listener) {
        mAct = activity;
        mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new Holder(mAct, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Holder h = (Holder) holder;
        h.mItemView.setTag(h);
        h.setData(getItem(position), position);
        h.mItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Holder holder = (Holder) v.getTag();
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, holder.model, holder.position);
        }

    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Activity mAct;
        View mItemView;
        ItemTopicBinding mBinding;
        TopicModel model;
        int position;
        private DisplayMetrics mDisplayMetrics;

        public Holder(Activity activity, View itemView) {
            super(itemView);
            mAct = activity;
            mItemView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.flComment.setOnClickListener(this);
            mBinding.ctvCollect.setOnClickListener(this);
            mBinding.flShare.setOnClickListener(this);
            mBinding.ctvFollow.setOnClickListener(this);

            mDisplayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        }

        public void setData(TopicModel model, int position) {
            this.model = model;
            this.position = position;

            mBinding.setTitle(model.getName());
            mBinding.setTime(TimeUtil.sortAddTime(model.getAddTime()));
            mBinding.setContent(model.getContent());
            mBinding.setCommentCount(model.getComment());
            mBinding.setIsFollow(!"0".equals(model.getIS_Follow()));
            mBinding.setIsCollected(!"0".equals(model.getIS_Keep()));

            //头像
            Glide.with(mItemView.getContext()).load(model.getHeadImg()).asBitmap().centerCrop().placeholder(R.mipmap.ic_default_head).into(new BitmapImageViewTarget(mBinding.ivIcon) {
                @Override
                protected void setResource(Bitmap resource) {
                    super.setResource(resource);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getView().getResources(), resource);
                    roundedBitmapDrawable.setCircular(true);
                    view.setImageDrawable(roundedBitmapDrawable);
                }
            });

            //图片
            if (!TextUtils.isEmpty(model.getImgs())) {

                String images[] = model.getImgs().split(",");
                ViewGroup.LayoutParams params = mBinding.gvImages.getLayoutParams();

                if (images.length == 1) {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = mDisplayMetrics.widthPixels * 2 / 3;
                    mBinding.gvImages.setNumColumns(1);
                } else {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mBinding.gvImages.setNumColumns(3);
                }

                mBinding.gvImages.setLayoutParams(params);

                mBinding.gvImages.setVisibility(View.VISIBLE);
                ImagesAdapter imagesAdapter = new ImagesAdapter(mAct);
                mBinding.gvImages.setAdapter(imagesAdapter);
                for (String image : images) {
                    imagesAdapter.addListData(Uri.parse(image), false);
                }
                imagesAdapter.notifyDataSetChanged();
            } else {

                mBinding.gvImages.setVisibility(View.GONE);
                mBinding.gvImages.setAdapter(null);
            }
            //标签
            if (!TextUtils.isEmpty(model.getLabel())) {
                mBinding.tagGroup.setTags(model.getLabel().split(","));
            } else {
                mBinding.tagGroup.setTags();
            }

//            model.getIS_Follow()
            mBinding.ctvFollow.setTag(model);
            setFollowText(model,mBinding.ctvFollow);

            if (model.getUserID().equals(User.getUser().getId())){
                mBinding.ctvFollow.setVisibility(View.GONE);
            }else{
                mBinding.ctvFollow.setVisibility(View.VISIBLE);
            }

            mBinding.flComment.setTag(model);
            mBinding.ctvFollow.setTag(model);
            mBinding.ctvCollect.setTag(model);
            mBinding.flShare.setTag(model);
        }

        public void enableClickComment(boolean enable) {
            mBinding.flComment.setClickable(enable);
        }


        private void setFollowText(TopicModel model,CheckedTextView ctv) {
            String follow=model.getIS_Follow();
            if ("0".equals(follow)){
                ctv.setText("+关注");
            }else{
                ctv.setText("已关注");
            }
        }

        /**
         * 请求关注
         */
        private void requestFollow(final TopicModel topicModel, final CheckedTextView ctv) {

            LoadDialog.showLoadDialog(ctv.getContext(), "请求中...");
            boolean isFollow = !ctv.isChecked();
            //    User_ID:用户ID
            //    Friend_ID:好友ID
            //    Type:1-关注 0-取消关注
            ctv.setClickable(false);
            Observable<BaseModel> observable = RequestHelper.requestAddFollow(String.valueOf(User.getUser().getId()), topicModel.getUserID(), isFollow ? 1 : 0);
            Subscription subscription = observable.subscribe(new MySubscriber<BaseModel>(ctv.getContext()) {

                @Override
                public void onCompleted() {
                    super.onCompleted();
                    if (ctv != null) {
                        ctv.postDelayed(() -> LoadDialog.dismissLoadDialog(), 400);
                        ctv.setClickable(true);
                    }
                }

                @Override
                public void onNext(BaseModel baseModel) {

                    if (baseModel.isSuccess() && ctv != null) {
                        ctv.setChecked(!ctv.isChecked());
                        topicModel.setIS_Follow(ctv.isChecked() ? "1" : "0");
                        setFollowText(topicModel,ctv);
                    } else {

                    }
                }
            });


        }

        /**
         * 请求收藏
         */
        private void requestCollection(final TopicModel topicModel, final CheckedTextView ctv) {
            LoadDialog.showLoadDialog(ctv.getContext(), "请求中...");
            boolean isCollected = !ctv.isChecked();
            //    收藏 ChangeCollect
            //    User_ID:用户ID
            //    Topic_ID:
            //    Type:1-收藏 0-取消收藏
            Observable<BaseModel> observable = RequestHelper.requestChangeCollect(String.valueOf(User.getUser().getId()), topicModel.getID(), isCollected ? 1 : 0);
            ctv.setClickable(false);
            observable.subscribe(new MySubscriber<BaseModel>() {
                @Override
                public void onCompleted() {
                    super.onCompleted();
                    ctv.setClickable(true);
                    ctv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadDialog.dismissLoadDialog();
                        }
                    }, 400);
                }

                @Override
                public void onNext(BaseModel baseModel) {
                    if (baseModel.isSuccess() && ctv != null) {
                        ctv.setChecked(!ctv.isChecked());
                        topicModel.setIS_Keep(ctv.isChecked() ? "1" : "0");
                    }
                }
            });
        }

        private void share() {
            //将标签转化为分享内容
            String[] strings = model.getLabel().split(",");
            String content = "";
            for (int i = 0; i < strings.length; i++) {
                content = content + "#" + strings[i]+"# ";
            }
            content = content+"  "+model.getContent();
            SheetDialog mSheetDialog = new SheetDialog(mAct,content,model.getShareUrl()).builder();
            mSheetDialog.show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fl_comment: {
                    TopicModel topicModel = (TopicModel) v.getTag();
                    TopicCommentActivity.start(mAct,topicModel,position);
                }
                break;
                case R.id.ctv_collect: {
                    TopicModel topicModel = (TopicModel) v.getTag();
                    CheckedTextView ctv = (CheckedTextView) v;
                    requestCollection(topicModel, ctv);
                }
                break;
                case R.id.ctv_follow: {
                    TopicModel topicModel = (TopicModel) v.getTag();
                    CheckedTextView ctv = (CheckedTextView) v;
                    requestFollow(topicModel, ctv);
                }
                break;
                case R.id.fl_share://分享
                    share();
                    break;
            }
        }

    }
}
