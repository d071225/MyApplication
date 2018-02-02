package com.dyy.newtest.ui.activity.launchmode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SingleTopActivity extends AppCompatActivity {
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
    @BindView(R.id.btn_singleTask_flags)
    Button btnSingleTaskFlags;
    @BindView(R.id.btn_singleTop_flags)
    Button btnSingleTopFlags;
    @BindView(R.id.btn_singleInstance_flags)
    Button btnSingleInstanceFlags;
    @BindView(R.id.et)
    EditText et;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        ButterKnife.bind(this);
        System.out.println("------SingleTopActivity onCreate----------" + getTaskId());
        sp = getSharedPreferences("hello", MODE_PRIVATE);
        Random random = new Random();
        Random random2 = new Random();
        Random random3 = new Random();
        btnSingleInstance.setBackgroundColor(Color.rgb(random.nextInt(255), random2.nextInt(255), random3.nextInt(255)));
    }

    @OnClick({R.id.btn_standard, R.id.btn_singleTop, R.id.btn_singleTask, R.id.btn_singleInstance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_standard:
                startActivity(new Intent(SingleTopActivity.this, StandardActivity.class));
                break;
            case R.id.btn_singleTop:
                Intent intent = new Intent(SingleTopActivity.this, SingleTopActivity.class);
                intent.putExtra("st", "this is a new string");
                startActivity(intent);
                break;
            case R.id.btn_singleTask:
                startActivity(new Intent(SingleTopActivity.this, SingleTaskActivity.class));
                break;
            case R.id.btn_singleInstance:
                startActivity(new Intent(SingleTopActivity.this, SingleInstanceActivity.class));
                break;
            case R.id.btn_singleTop_flags:
                Intent intent2 = new Intent(SingleTopActivity.this, FlagsSingleTopActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                break;
            case R.id.btn_singleTask_flags:
                Intent intent3 = new Intent(SingleTopActivity.this, FlagsSingleTaskActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent3);
                break;
            case R.id.btn_singleInstance_flags:
                Intent intent4 = new Intent(SingleTopActivity.this, FlagsSingleInstanceActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent4);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LogUtils.a("---**---SingleTopActivity onNewIntent---**---");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String st = getIntent().getStringExtra("st");
        tv.setText("SingleTopActivity--" + count++ + ";st:" + st);
        if (sp != null) {
            String str = sp.getString("text", "default");
            tv.setText(str);
        }

//        Intent intent=new Intent();
//        intent.putExtra("result","返回的结果");
//        setResult(001,intent);
//        finish();
        LogUtils.a("------SingleTopActivity onResume------获取的intent值为：" + st);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.a("------SingleTopActivity onRestart------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor edit = sp.edit();
        String name = getClass().getName();
        String text = et.getText().toString();
        LogUtils.a("------SingleTopActivity onPause------ className:"+name);
        edit.putString("className", name);
        edit.putString("text", text);
        edit.commit();
//        LogUtils.a("------SingleTopActivity onPause------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.a("------SingleTopActivity onStop------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.a("------SingleTopActivity onDestroy------");
    }
}
