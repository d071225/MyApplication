package com.dyy.newtest.retrofitInterface;

import com.dyy.newtest.bean.GirlResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DY on 2018/1/5.
 */

public interface GirlApi {
    @GET("listjson?pn=0&rn=200&tag1=美女&tag2=小清新&ie=utf8")
    Call<GirlResponse> getResult();
    @GET("listjson?pn=0&rn=5&tag1=美女&tag2=小清新&ie=utf8")
    Call<GirlResponse> getHeaderResult();
}
