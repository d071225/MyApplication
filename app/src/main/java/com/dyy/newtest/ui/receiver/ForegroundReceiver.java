package com.dyy.newtest.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.ui.activity.service.ForegroundActivity;
import com.dyy.newtest.ui.service.ForegroundService;

/**
 * Created by DY on 2018/3/14.
 */

public class ForegroundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.e("收到广播 action="+action);
        if (action.equals(ForegroundService.PLAY)) {
            String key = intent.getStringExtra("key");
            LogUtils.e("收到广播 key="+key);
            switch (key) {
                case ForegroundService.PLAY:
                    gotoForegroundActivity(context, ForegroundService.PLAY);
                    break;
                case ForegroundService.PAUSE:
                    gotoForegroundActivity(context, ForegroundService.PAUSE);
                    break;
                case ForegroundService.STOP:
                    gotoForegroundActivity(context, ForegroundService.STOP);
                    break;
            }
        }
    }
    public void gotoForegroundActivity(Context context,String key){
        Intent intent=new Intent(context, ForegroundActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle=new Bundle();
        bundle.putSerializable("key",key);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
