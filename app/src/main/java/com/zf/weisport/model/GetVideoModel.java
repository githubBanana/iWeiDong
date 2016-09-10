package com.zf.weisport.model;


import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-07 16:38
 * @email Xs.lin@foxmail.com
 */
public class GetVideoModel extends BaseModel<GetVideoModel> {

    public String rowNum;
    public String ID;
    public String Title;
    public String Abstract;
    public String AddTime;
    public String LogoUrl;
    public String url;

    @Override
    public String toString() {
        return "GetVideoModel{" +
                "rowNum='" + rowNum + '\'' +
                ", ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", Abstract='" + Abstract + '\'' +
                ", AddTime='" + AddTime + '\'' +
                ", LogoUrl='" + LogoUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
