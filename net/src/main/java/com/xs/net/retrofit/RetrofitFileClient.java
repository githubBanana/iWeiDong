package com.xs.net.retrofit;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-01 17:50
 * @email Xs.lin@foxmail.com
 */
public class RetrofitFileClient {


    private static ApiService mApiService;

    static {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Response response = chain.proceed(chain.request());
            ResponseBody responseBody = response.body();
            String result = responseBody.string();
            Log.e("Client", "intercept#result no decrypt:" + result);
           /* result = DESUtil.decryptDoNet(result);*/
            Log.e("Client", "intercept#result:" + result);
            ResponseBody newResponseBody = ResponseBody.create(responseBody.contentType(), result);
            Response newResponse = response.newBuilder().body(newResponseBody).build();
            return newResponse;
        }).build();

//        初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetConfig.getFileUploadWebService())
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
        private Map<String, RequestBody> map;

        public Builder() {}
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder map(Map<String, RequestBody> map) {
            this.map = map;
            return this;
        }

        public <T> Observable<T> post3(Class<T> cla) {
            Observable<String> observable = getApiService().post3(method, map)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            return observable.map(s -> new Gson().fromJson(s, cla));
        }

    }


}

