package com.dyy.newtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dyy.newtest.R;
import com.dyy.newtest.model.AllUser;
import com.dyy.newtest.model.WeixinObserver;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 建造者模式
 */
public class BuliderActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulider);
        ButterKnife.bind(this);
        startActivity(new Intent(this,RxJava2Activity.class));
        /**
         * 建造者模式
         */
//        TextViewBulider bulider=new TextViewBulider(tv);
//        bulider.setMyTextSize(100);
//        bulider.setMyText("hello bulider");
//        bulider.setMyTextColor(Color.RED);
//        bulider.create();
        /**
         * 简单工程模式
         */
//        ComputerFactory.create("联想").start();
//        ComputerFactory.create("惠普").start();
//        ComputerFactory.create("华硕").start();
        /**
         * 观察者模式
         */
        WeixinObserver wxob=new WeixinObserver("张三");
        WeixinObserver wxob1=new WeixinObserver("李四");
        WeixinObserver wxob2=new WeixinObserver("王五");
        AllUser allUser=new AllUser();
        allUser.attach(wxob);
        allUser.attach(wxob1);
        allUser.attach(wxob2);
        allUser.notify("大家好");
    }
}
