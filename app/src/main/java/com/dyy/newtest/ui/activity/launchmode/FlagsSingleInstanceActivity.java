package com.dyy.newtest.ui.activity.launchmode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DY on 2018/1/26.
 */

public class FlagsSingleInstanceActivity extends AppCompatActivity {

    @BindView(R.id.btn_standard)
    Button btnStandard;
    @BindView(R.id.btn_singleTop)
    Button btnSingleTop;
    @BindView(R.id.btn_singleTask)
    Button btnSingleTask;
    @BindView(R.id.btn_singleInstance)
    Button btnSingleInstance;
    private static int count = 1;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        ButterKnife.bind(this);
        System.out.println("------FlagsSingleInstanceActivity onCreate----------" + getTaskId());
        tv.setText("FlagsSingleInstanceActivity--"+count++);
        Random random = new Random();
        Random random2 = new Random();
        Random random3 = new Random();
        btnSingleInstance.setBackgroundColor(Color.rgb(random.nextInt(255), random2.nextInt(255), random3.nextInt(255)));
    }

    @OnClick({R.id.btn_standard, R.id.btn_singleTop, R.id.btn_singleTask, R.id.btn_singleInstance,R.id.btn_singleTop_flags
    ,R.id.btn_singleTask_flags,R.id.btn_singleInstance_flags})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_standard:
                startActivity(new Intent(FlagsSingleInstanceActivity.this, StandardActivity.class));
                break;
            case R.id.btn_singleTop:
                Intent intent = new Intent(FlagsSingleInstanceActivity.this, SingleTopActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_singleTask:
                Intent intent1 = new Intent(FlagsSingleInstanceActivity.this, SingleTaskActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_singleInstance:
                startActivity(new Intent(FlagsSingleInstanceActivity.this, SingleInstanceActivity.class));
                break;
            case R.id.btn_singleTop_flags:
                Intent intent2 = new Intent(FlagsSingleInstanceActivity.this, FlagsSingleTopActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                break;
            case R.id.btn_singleTask_flags:
                Intent intent3 = new Intent(FlagsSingleInstanceActivity.this, FlagsSingleTaskActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent3);
                break;
            case R.id.btn_singleInstance_flags:
                Intent intent4 = new Intent(FlagsSingleInstanceActivity.this, FlagsSingleInstanceActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.a("---**---FlagsSingleInstanceActivity onNewIntent---**---");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=new Intent();
        intent.putExtra("result","FlagsSingleTaskActivity");
        setResult(3,intent);
        LogUtils.a("------FlagsSingleInstanceActivity onResume------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.a("------FlagsSingleInstanceActivity onRestart------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.a("------FlagsSingleInstanceActivity onPause------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.a("------FlagsSingleInstanceActivity onStop------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.a("------FlagsSingleInstanceActivity onDestroy------");
    }
}