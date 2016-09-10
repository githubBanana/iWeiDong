package com.zf.widget.carousel;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zf.widget.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hyc on 16/4/12.
 *
 */
public class SquaViewPagerAdapter extends PagerAdapter {

    public void setStrings(List<HashMap<String,Object>> strings, Activity context) {
        this.mContext = context;
        _strings = strings;
        notifyDataSetChanged();
    }

    private Activity mContext;
    private  List<HashMap<String,Object>> _strings;

    public List<HashMap<String,Object>> getStrings() {
        return _strings;
    }

    private LayoutInflater _inflater;
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView((View) object);//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {  //这个方法用来实例化页卡
        if (_inflater==null)
            _inflater = LayoutInflater.from(container.getContext());
        View rootview = _inflater.inflate(R.layout.item_image, container, false);
        final ImageView imageView = (ImageView) rootview.findViewById(R.id.image_item);
        HashMap map = _strings.get(position % _strings.size());
        final String path = (String) map.get("filePath");
        if (path != null)
            ImageLoader.getInstance().displayImage(path,imageView);
        container.addView(rootview);//添加页卡

        final int p = position;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sortPosition = 0;
                if (p < _strings.size())
                    sortPosition = p;
                else
                    sortPosition = p % _strings.size();
                String url = (String) _strings.get(sortPosition).get("url");
//                ActivityUtil.startWebActivity(mContext,url, WebActivity.WEB_VIDEO);
            }
        });
        return rootview;
    }

    @Override
    public int getCount() {
    return  Integer.MAX_VALUE/2+1;//返回页卡的数量
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;//官方提示这样写
    }

}
