package com.dyy.newtest.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeakCanaryActivity extends AppCompatActivity {

    @BindView(R.id.btn_start_leak_canary)
    Button btnStartLeakCanary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_leak_canary)
    public void onClick() {
        startAsyncTask();
    }
    public void startAsyncTask(){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                LogUtils.e("===doInBackground===");
                SystemClock.sleep(20000);
                LogUtils.e("sleep结束");
                return null;
            }
        }.execute();
    }
}
