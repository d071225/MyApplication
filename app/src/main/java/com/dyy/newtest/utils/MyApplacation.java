package com.dyy.newtest.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by DY on 2018/1/9.
 */

public class MyApplacation extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LeakCanary.install(this);
        int pid = Process.myPid();
        String processName=null;
        ActivityManager manager= (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo: manager.getRunningAppProcesses()){
            if (processInfo.pid==pid){
                processName=processInfo.processName;
            }
        }
        LogUtils.a("processName:"+processName);
        mContext = this;
    }

}
