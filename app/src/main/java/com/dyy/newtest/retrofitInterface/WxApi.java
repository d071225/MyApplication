package com.dyy.newtest.retrofitInterface;

import com.dyy.newtest.bean.ApiResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by DY on 2018/1/4.
 */

public interface WxApi {
    @FormUrlEncoded
    @POST("wx/msg.do")
    Call<ApiResponse> getResult(@FieldMap Map<String,String> map);
    @FormUrlEncoded
    @POST("wx/msg.do")
    Observable<ApiResponse> getRxResult(@FieldMap Map<String,String> map);
//    Call<ApiResponse> getResult(@Body WxRequest request);
}
