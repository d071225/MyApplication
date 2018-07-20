package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dyy.newtest.R;
import com.dyy.newtest.bean.ApiResponse;
import com.dyy.newtest.bean.GetYDResponse;
import com.dyy.newtest.retrofitInterface.GetYouDaoApi;
import com.dyy.newtest.retrofitInterface.WxApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.btn_req)
    Button btnReq;
    @BindView(R.id.text_content)
    TextView textContent;
    private static final String TAG="RetrofitActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        Log.e(TAG,"onCreate---"+Thread.currentThread().getId());
    }

    @OnClick(R.id.btn_req)
    public void onViewClicked() {
//        getData();
        getYDData();
    }
    public void getYDData(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetYouDaoApi getYouDaoApi = retrofit.create(GetYouDaoApi.class);
        Call<GetYDResponse> call = getYouDaoApi.getResult();
        call.enqueue(new Callback<GetYDResponse>() {
            @Override
            public void onResponse(Call<GetYDResponse> call, Response<GetYDResponse> response) {
                Log.e(TAG,"返回数据---"+response+";"+Thread.currentThread().getId());
                GetYDResponse body = response.body();
                Log.e(TAG,"返回数据 body---"+ body.getContent().toString());
                textContent.setText(body.getContent().toString());
            }

            @Override
            public void onFailure(Call<GetYDResponse> call, Throwable t) {
                Log.e(TAG,"请求失败---");
            }
        });

    }
    public void getData(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.10.110:8380/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WxApi wxApi=retrofit.create(WxApi.class);
        Map<String,String> map=new TreeMap<>();
        map.put("app_id","wx68a4cf17e7f4096b");
        map.put("msg_content","{\"first\": {\"value\":\"恭喜你购买成功！\",\"color\":\"#173177\"},\"keynote1\":{\"value\":\"巧克力\",\"color\":\"#173177\"},\"keynote2\": {\"value\":\"39.8元\",\"color\":\"#173177\"},\"keynote3\": {\"value\":\"2014年9月22日\",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\",\"color\":\"#173177\"}}");
        map.put("template_id","ZldEcxWNYP0-0AmTuRJDGVQDL1-xJhBp4yt-ouyRq4I");
        map.put("sys_id","PA001");
        map.put("mcht_msg_no","20180104");
        map.put("sign_type","01");
        map.put("sign","145aeda963a2f8dd44a3071e8f6ec4db");
//        WxRequest request=new WxRequest();
//        request.setApp_id("123");
        Call<ApiResponse> resultCall = wxApi.getResult(map);
        resultCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.e(TAG,"返回数据---"+response);
                ApiResponse apiResponse = response.body();
                String message = apiResponse.getMessage();
                Log.e(TAG,"返回数据 body---"+ message);
                textContent.setText(message);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG,"请求失败---");
            }
        });
    }
}
