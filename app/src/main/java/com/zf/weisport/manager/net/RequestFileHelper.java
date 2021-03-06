package com.zf.weisport.manager.net;

import com.xs.net.retrofit.RetrofitFileClient;
import com.zf.weisport.model.FileModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * @version V1.0 <文件上传>
 * @author: Xs
 * @date: 2016-04-08 15:31
 * @email Xs.lin@foxmail.com
 */
public class RequestFileHelper {

    public static final String WEB_SERVICE = "http://wd.zfwsc.com/Admin/File/";

    private static final RequestFileHelper INSTANCE = new RequestFileHelper();
    private RequestFileHelper() {}
    public static final RequestFileHelper getInstance() {
        return INSTANCE;
    }

    /**
     * 图片上传
     * @param imgFile
     * @return
     */
    public Observable<FileModel> upload(File imgFile) {
        Map<String, RequestBody> map = new HashMap<>();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), imgFile);
        map.put("image\"; filename=\""+imgFile.getName()+"", fileBody);
        return new RetrofitFileClient.Builder().method("Upload").map(map).post3(FileModel.class);
    }





}
