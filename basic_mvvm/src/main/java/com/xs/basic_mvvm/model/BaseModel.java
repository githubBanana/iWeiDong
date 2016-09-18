package com.xs.basic_mvvm.model;

import java.io.Serializable;
import java.util.List;

/**
 * 深圳州富科技有限公司
 * Created by lwg on 2016/2/1.
 */
public class BaseModel<T> implements Serializable {

    String ErrNum;
    String ErrMsg;
    List<T> data;
    int PageCount;
    int TotalCount;

    public int getDataCount() {
        if (PageCount == 0)
            return TotalCount;
        return PageCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public String getErrNum() {
        return ErrNum;
    }

    public void setErrNum(String errNum) {
        ErrNum = errNum;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public boolean isSuccess() {
        return "0".equals(ErrNum);
    }

    public boolean isEmptyData() {
        return !"0".equals(ErrNum) || data == null || data.isEmpty() ;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "ErrNum='" + ErrNum + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                ", data=" + data +
                ", PageCount=" + PageCount +
                ", TotalCount=" + TotalCount +
                '}';
    }
}
