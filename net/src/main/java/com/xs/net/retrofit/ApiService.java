package com.xs.net.retrofit;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {


    @Headers("Cache-Control:max-age=640000")
    @FormUrlEncoded
    @POST("{method}")
    Observable<String> post(@Path("method") String method, @FieldMap Map<String, Object> fieldMap);

    @POST("{method}")
    Observable<String> post2(@Path("method") String method);

    @Multipart
    @POST("{method}")
    Observable<String> post3(@Path("method") String method, @PartMap Map<String, RequestBody> params);


//    @Headers("Cache-Control:max-age=64000000")
////    @FormUrlEncoded
//@GET("{method}")
//Call<TopicModel> getTopic(@Path("method") String method, @Query("json") String json);

}
