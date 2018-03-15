package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dyy.newtest.R;
import com.dyy.newtest.ui.fragment.MyDialog;

public class AddGoodsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),"01");
    }
}
