package com.dyy.newtest.retrofitInterface;

import com.dyy.newtest.bean.GetYDResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DY on 2018/1/4.
 */

public interface GetYouDaoApi {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<GetYDResponse> getResult();
}
