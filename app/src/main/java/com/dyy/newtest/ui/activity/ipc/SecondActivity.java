package com.dyy.newtest.ui.activity.ipc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dyy.newtest.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        LogUtils.a("User.count==="+ User.count);
//        UserParcelable user1 = getIntent().getParcelableExtra("par");
//        LogUtils.a(user1.getName()+";年龄"+user1.getAge()+";密码"+user1.getPassword());
    }
}
