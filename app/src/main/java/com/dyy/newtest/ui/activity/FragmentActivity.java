package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.ui.fragment.CityFragment;
import com.dyy.newtest.ui.fragment.ProFragment;

public class FragmentActivity extends android.support.v4.app.FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        LogUtils.e("onCreate");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fg_left,new ProFragment());
        ft.add(R.id.fg_right,new CityFragment());
        ft.commit();
    }
}
