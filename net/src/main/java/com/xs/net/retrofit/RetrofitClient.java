package com.xs.net.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version V1.0 <Retrofit client>
 * @author: Xs
 * @date: 2016-09-01 16:01
 * @email Xs.lin@foxmail.com
 */
public class RetrofitClient {


    public static final int PAGESIZE = 10;
    private static ApiService mApiService;

    static {

        File externalStorageDirectory = android.os.Environment.getExternalStorageDirectory();
//        String externalStorageDirectory = FileUtils.getCacheDir();
        okhttp3.Cache cache = new okhttp3.Cache(new File(externalStorageDirectory, "okhttp/cache"), 1024 * 1024);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Response response = chain.proceed(chain.request());
            ResponseBody responseBody = response.body();
            String result = responseBody.string();
            Log.e("Client", "intercept#result no decrypt:" + result);
            result = DESUtil.decryptDoNet(result);
            Log.e("Client", "intercept#result:" + result);
            ResponseBody newResponseBody = ResponseBody.create(responseBody.contentType(), result);
            return response.newBuilder().body(newResponseBody).build();
        }).cache(cache).build();

//        初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetConfig.getWebService())
                .client(client)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mApiService = retrofit.create(ApiService.class);
    }

    private static ApiService getApiService() {
        return mApiService;
    }

    public static final class Builder {

        private String method ;
        private Map<String, Object> map;

        public Builder() {}
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder map(Map<String, Object> map) {
            this.map = map;
            return this;
        }

        /**
         * 请求网络入口,有参请求
         * @param cla
         * @param <T>
         * @return
         */
        public <T> Observable<T>  post(Class<T> cla) {
            if (map == null || TextUtils.isEmpty(method))
                throw new NullPointerException("there is no method or map");
            Observable<String> observable = getApiService().post(method, encryptMap(map)).
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread());
            return observable.map(s -> new Gson().fromJson(s, cla));
        }

        /**
         * 请求网络入口,无参请求
         * @param cla
         * @param <T>
         * @return
         */
        public <T> Observable<T>  post2(Class<T> cla) {
            if (TextUtils.isEmpty(method))
                throw new NullPointerException("there is no method");
            Observable<String> observable = getApiService().post2(method).
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread());
            return observable.map(s -> new Gson().fromJson(s, cla));
        }

    }

    /**
     * 加密Map
     *
     * @return
     */
    private static Map<String, Object> encryptMap(Map<String, Object> map) {

        String json = new Gson().toJson(map);
        String encryptContent = DESUtil.encryptAsDoNet(json);
        map.clear();
        Log.e("RequestHelper", "encryptMap start:" + json);
        map.put("json", encryptContent);
        Log.e("RequestHelper", "encryptMap end:" + encryptContent);
        return map;
    }

    /**
     * 解密Map
     *
     * @param encryptInfo
     * @return
     */
    public Map<String, Object> encryptDoMap(String encryptInfo) {
        String decryptInfo = DESUtil.decryptDoNet(encryptInfo);
        return getMapForJson(decryptInfo);
    }

    /**
     * Json 转成 Map<>
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> getMapForJson(String jsonStr) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonStr);
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            String value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.getString(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }



    /* ***************以下为测试 Start********************** */
    public static void okhttp() {
        RequestBody body = new FormBody.Builder().add("id", "1").add("token_key", "d83a9af0461bf884bd5e506e1bc7eb81").build();
        final Request request = new Request.Builder().url(NetConfig.getHOST()).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("okhttp", "onFailure", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("okhttp", "onResponse " + response.message() + "   " + response.body().string());
            }
        });
    }

    public static void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("token_key", "d83a9af0461bf884bd5e506e1bc7eb81");
        retrofit2.Call call = (retrofit2.Call) getApiService().post("intro", map);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                Log.e("okhttp", "onResponse " + response.message() + "   " + response.body());

            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                Log.e("okhttp", "onFailure", t);
            }
        });
    }

    public static void test2() {
//        Map<String, String> map = new HashMap<>();
//        map.put("Mobile", "13428283740");
//        map.put("Type", "1");
//        Observable<BaseModel<String>> o = getApiService().post2("/WebService/WeiYunDong.asmx/GetSMSCode", map);
//        o.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<BaseModel<String>>() {
//            @Override
//            public void onCompleted() {
//                Log.e("okhttp", "Observable onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("okhttp", "Observable onError", e);
//            }
//
//            @Override
//            public void onNext(BaseModel<String> s) {
//                Log.e("okhttp", "Observable onNext  " + s);
//            }
//        });
    }


}
