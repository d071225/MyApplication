package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.dyy.newtest.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity {
    private static final String TAG="OkhttpActivity";
    @BindView(R.id.request)
    Button request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.request)
    public void onViewClicked() {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request build = new Request.Builder().url("http://192.168.10.110:8380/api/wx/msg.do").get().build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"请求失败---");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG,"返回数据---"+response);
                Log.e(TAG,"返回数据 body---"+response.body().string());
            }
        });
    }
}
