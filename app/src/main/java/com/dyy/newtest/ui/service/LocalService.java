package com.dyy.newtest.ui.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.ServiceAidlInterface;
import com.dyy.newtest.ui.receiver.KeepliveReceiver;

/**
 * Created by DY on 2018/3/19.
 */

public class LocalService extends Service {

    private LocalBinder localBinder;
    private LocalConn localConn;
    private KeepliveReceiver mReceiver;
    public static final String ALIVE="com.dyy.newtest.alive";

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("-------onBind----------");
        if (localBinder==null){
            localBinder = new LocalBinder();
        }
        return localBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("-------onCreate----------");
        localConn = new LocalConn();
//        mReceiver = new KeepliveReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ALIVE);
//        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("本地服务活了");
        bindService(new Intent(LocalService.this,RemoteService.class),localConn,BIND_AUTO_CREATE);
//        return START_REDELIVER_INTENT;
        return START_STICKY;
    }
    public class LocalConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("成功绑定远程服务");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("远程服务被杀死了，重启远程服务");
            startService(new Intent(LocalService.this,RemoteService.class));
            bindService(new Intent(LocalService.this,RemoteService.class),localConn,BIND_AUTO_CREATE);
        }
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("-------onDestroy----------");
        sendBroadcast(new Intent(ALIVE),"com.dyy.newtest.ALIVE_PERMISSION");//带权限的广播发送
//        sendBroadcast(new Intent(ALIVE));
//        if (mReceiver!=null) {
//            unregisterReceiver(mReceiver);
//        }
        startService(new Intent(LocalService.this,RemoteService.class));
        bindService(new Intent(LocalService.this,RemoteService.class),localConn,BIND_AUTO_CREATE);
    }
    public class LocalBinder extends ServiceAidlInterface.Stub {

        @Override
        public void doSomething() throws RemoteException {
            LogUtils.e("-------doSomething----------");
        }
    }
}
