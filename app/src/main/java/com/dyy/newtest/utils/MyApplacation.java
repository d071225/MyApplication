package com.dyy.newtest.utils;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by DY on 2018/1/9.
 */

public class MyApplacation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
