package com.dyy.newtest.model;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.model.modelInterface.Observer;

/**
 * Created by DY on 2018/1/15.
 */

public class WeixinObserver implements Observer {
    private String name;

    public WeixinObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String msg) {
        LogUtils.e(name+"==="+msg);
    }
}
