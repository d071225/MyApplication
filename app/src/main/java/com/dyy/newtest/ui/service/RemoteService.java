package com.dyy.newtest.ui.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.ServiceAidlInterface;

/**
 * Created by DY on 2018/3/19.
 */

public class RemoteService extends Service {

    private RemoteBinder remoteBinder;
    private RemoteConn remoteConn;

    @Override
    public IBinder onBind(Intent intent) {
        if (remoteBinder==null){
            remoteBinder = new RemoteBinder();
        }
        return remoteBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        remoteConn = new RemoteConn();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("远程服务活了");
        bindService(new Intent(RemoteService.this,LocalService.class),remoteConn,BIND_AUTO_CREATE);
        return START_STICKY;
    }
    public class RemoteConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("本地服务被杀死了");
            startService(new Intent(RemoteService.this,LocalService.class));
            bindService(new Intent(RemoteService.this,LocalService.class),remoteConn,BIND_AUTO_CREATE);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(RemoteService.this,LocalService.class));
        bindService(new Intent(RemoteService.this,LocalService.class),remoteConn,BIND_AUTO_CREATE);
    }
    public class RemoteBinder extends ServiceAidlInterface.Stub {

        @Override
        public void doSomething() throws RemoteException {
            LogUtils.e("-------doSomething----------");
        }
    }
}
