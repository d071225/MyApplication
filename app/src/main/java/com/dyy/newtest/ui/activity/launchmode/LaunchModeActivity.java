package com.dyy.newtest.ui.activity.launchmode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LaunchModeActivity extends AppCompatActivity {

    @BindView(R.id.btn_standard)
    Button btnStandard;
    @BindView(R.id.btn_singleTop)
    Button btnSingleTop;
    @BindView(R.id.btn_singleTask)
    Button btnSingleTask;
    @BindView(R.id.btn_singleInstance)
    Button btnSingleInstance;
    @BindView(R.id.btn_singleTask_flags)
    Button btnSingleTaskFlags;
    @BindView(R.id.btn_singleTop_flags)
    Button btnSingleTopFlags;
    @BindView(R.id.btn_singleInstance_flags)
    Button btnSingleInstanceFlags;
    @BindView(R.id.tv)
    TextView tv;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        ButterKnife.bind(this);
        System.out.println("------LaunchModeActivity onCreate----------"+getTaskId()+";类名:"+getClass().getName());
        sp = getSharedPreferences("hello", MODE_PRIVATE);
        if (sp!=null){
            String className = sp.getString("className", null);
            System.out.println("------LaunchModeActivity onCreate----------类名:"+className);
            if (className!=null) {
                try {
                    Class<?> aClass = Class.forName(className);
                    startActivity(new Intent(this, aClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick({R.id.btn_standard, R.id.btn_singleTop, R.id.btn_singleTask, R.id.btn_singleInstance, R.id.btn_singleTop_flags
            , R.id.btn_singleTask_flags, R.id.btn_singleInstance_flags})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_standard:
                startActivity(new Intent(LaunchModeActivity.this, StandardActivity.class));
                break;
            case R.id.btn_singleTop:
                startActivity(new Intent(LaunchModeActivity.this, SingleTopActivity.class));
                break;
            case R.id.btn_singleTask:
//                Intent intent = new Intent(LaunchModeActivity.this, SingleTaskActivity.class);
//                startActivityForResult(intent, 2);
                startActivity(new Intent(LaunchModeActivity.this, SingleTaskActivity.class));
                break;
            case R.id.btn_singleInstance:
//                Intent intent1 = new Intent(LaunchModeActivity.this, SingleInstanceActivity.class);
//                startActivityForResult(intent1, 2);
                startActivity(new Intent(LaunchModeActivity.this, SingleInstanceActivity.class));
                break;
            case R.id.btn_singleTop_flags:
                Intent intent2 = new Intent(LaunchModeActivity.this, FlagsSingleTopActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent2, 2);
                break;
            case R.id.btn_singleTask_flags:
                Intent intent3 = new Intent(LaunchModeActivity.this, FlagsSingleTaskActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent3, 2);
                break;
            case R.id.btn_singleInstance_flags:
                Intent intent4 = new Intent(LaunchModeActivity.this, FlagsSingleInstanceActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivityForResult(intent4, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.a("onActivityResult----requestCode:" + requestCode + ";resultCode:" + resultCode);
//        tv.setText(data.getStringExtra("result"));
    }
}
