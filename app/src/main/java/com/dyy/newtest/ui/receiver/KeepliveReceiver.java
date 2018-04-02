package com.dyy.newtest.ui.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.ui.service.LocalService;

import java.util.List;

public class KeepliveReceiver extends BroadcastReceiver {
    public static final String LocalServiceName="com.dyy.newtest.ui.service.LocalService";
    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.e("action="+action);
        if (action.equals(LocalService.ALIVE)){
            LogUtils.e("isServiceAlive:"+isServiceAlive(context));
            if (!isServiceAlive(context)){
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.startService(new Intent(context,LocalService.class));
                    }
                },10*1000);
            }
        }
    }
    public boolean isServiceAlive(Context context){
        boolean isAlive=false;
        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        if (runningServices==null) return isAlive;
        for (int i=0;i<runningServices.size();i++){
            String name = runningServices.get(i).service.getClassName().toString();
            if (name.equals(LocalServiceName)){
                isAlive=true;
                break;
            }
        }
        return isAlive;
    }
}
