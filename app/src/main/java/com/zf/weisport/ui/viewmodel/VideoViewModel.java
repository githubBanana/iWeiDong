package com.zf.weisport.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.GetVideoModel;
import com.zf.weisport.presenter.IVideoView;
import com.zf.weisport.presenter.biz.IVideoBiz;
import com.zf.weisport.presenter.biz.impl.VideoBizImpl;
import com.zf.weisport.ui.callback.IVideoCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-10 16:15
 * @email Xs.lin@foxmail.com
 */
public class VideoViewModel extends BaseViewModel<IVideoCallback,GetVideoModel> implements IVideoView {

    private int Type;
    private int PageIndex;
    private List<GetVideoModel> mList;

    private IVideoBiz biz;
    public VideoViewModel(IVideoCallback iVideoCallback) {
        super(iVideoCallback);
        this.mList  = new ArrayList<>();
        this.biz    = getBiz();
    }

    /**
     * 获取视频信息
     */
    public void getVideo() {
        biz.getVideo();
    }

    @Override
    protected BaseBiz createBiz() {
        return new VideoBizImpl(this,this);
    }

    @Override
    public void onGetVideoCompleted(List<GetVideoModel> getVideoModels) {
        setmList(getVideoModels);
        getCallback().onGetVideoSuccess();
    }

    @Override
    public void onNetErrorNotifyUI() {
        getCallback().onNetError();
    }

    @Override
    public void onNetEmptyNotifyUI() {
        getCallback().onNetEmpty();
    }

    @Override
    public void setType(int type) {
        this.Type = type;
    }

    @Override
    public int getType() {
        return this.Type;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.PageIndex = pageIndex;
    }

    @Override
    public int getPageIndex() {
        return this.PageIndex;
    }

    public List<GetVideoModel> getmList() {
        return this.mList;
    }

    public void setmList(List<GetVideoModel> mList) {
        this.mList = mList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mList = null;
    }
}
