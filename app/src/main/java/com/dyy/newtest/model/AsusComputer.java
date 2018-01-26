package com.dyy.newtest.model;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.model.modelInterface.Computer;

/**
 * Created by DY on 2018/1/15.
 */

public class AsusComputer extends Computer{
    @Override
    public void start() {
        LogUtils.e("华硕电脑启动了===");
    }
}
