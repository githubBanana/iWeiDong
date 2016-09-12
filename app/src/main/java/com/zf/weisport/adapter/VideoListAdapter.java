package com.zf.weisport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zf.weisport.R;
import com.zf.weisport.model.GetVideoModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class VideoListAdapter extends BaseAdapter{
    private List<GetVideoModel> _getVideoModels;
    private LayoutInflater _layoutInflater;

    public List<GetVideoModel> getGetVideoModels() {
        return _getVideoModels;
    }
    public int getGetVideoModelsSize() {
        if (_getVideoModels == null)
            return 0;
        return _getVideoModels.size();
    }
    public void setGetVideoModels(List<GetVideoModel> getVideoModels) {
        _getVideoModels = getVideoModels;
        notifyDataSetChanged();
    }

    @Override
    public GetVideoModel getItem(int position) {
        return _getVideoModels.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        if (_getVideoModels!=null)
            return _getVideoModels.size();
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (_layoutInflater==null)
            _layoutInflater = LayoutInflater.from(parent.getContext());
        VideoViewHolder holder;
        if (v == null) {
            holder = new VideoViewHolder();
            v = _layoutInflater.inflate(R.layout.item_list_video,null);
            holder.icon = (ImageView) v.findViewById(R.id.video_icon_bg);
            holder.play = (ImageView) v.findViewById(R.id.play_btn);
            holder.title = (TextView) v.findViewById(R.id.tv_title);
            holder.time = (TextView) v.findViewById(R.id.tv_time);
            v.setTag(holder);
        } else {
            holder = (VideoViewHolder) v.getTag();
        }
//        holder.icon.setBackground(ImageUtil.bitmapToDrawable(ImageLoader.getInstance().loadImageSync(mList.get(position).get("LogoUrl"))));
        GetVideoModel model = getItem(position);
        ImageLoader.getInstance().displayImage(model.LogoUrl,holder.icon);
        holder.title.setText(model.Title);
        holder.time.setText(model.AddTime);
        return v;
    }

    public void setDatas(List<GetVideoModel> data) {
        getGetVideoModels().addAll(data);
        notifyDataSetChanged();
    }

    public class VideoViewHolder {
        private ImageView icon;
        private ImageView play;
        private TextView title;
        private TextView time;
    }
}
