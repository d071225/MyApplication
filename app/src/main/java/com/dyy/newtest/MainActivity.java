package com.dyy.newtest;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dyy.newtest.bean.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.first)
    TextView first;
    @BindView(R.id.secent)
    TextView secent;
    @Nullable
    @BindView(R.id.third)
    TextView third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.percent_linlayout);
        ButterKnife.bind(this);
        User user=new User();
        user.setAge(15);
        user.setName("123");
        user.setPassword("123");
    }
    @Optional
    @OnClick({R.id.first, R.id.secent, R.id.third})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.first:
                first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setShow(null);
                        showId(R.mipmap.ic_launcher);
                    }
                });
                break;
            case R.id.secent:
                secent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setShow("第二行");
                    }
                });
                break;
            case R.id.third:
                third.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setShow("第三行");
                    }
                });
                break;
        }
    }
    private void setShow(@Nullable String msg) {
        Toast.makeText(MainActivity.this,"点击了"+msg,Toast.LENGTH_SHORT).show();
    }
    @RequiresPermission(Manifest.permission.INTERNET)
    private void showId( int msg) {
        Toast.makeText(MainActivity.this,getString(msg),Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL("http://www.baidu.com");
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int responseCode = conn.getResponseCode();
                    String responseMessage = conn.getResponseMessage();
                    System.out.println("responseMessage:"+responseMessage+"responseCode;"+responseCode);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
