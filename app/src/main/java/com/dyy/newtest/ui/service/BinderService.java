package com.dyy.newtest.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

public class BinderService extends Service {

    private MediaPlayer mediaPlayer;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private Notification notification;
    private int NO_3=3;

    public BinderService() {
    }
    public class MyBinder extends Binder{
        BinderService getService(){
            return BinderService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        LogUtils.e("----------onBind-------------");
       return new MyBinder();
    }

    @Override
    public void onCreate() {
//        if (mediaPlayer ==null) {
//            mediaPlayer = MediaPlayer.create(this, R.raw.ftm_ssjs);
//            mediaPlayer.setLooping(false);
//            mediaPlayer.start();
//        }
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon_map);
        builder.setContentTitle("下载");
        builder.setContentText("下载中");
        builder.setProgress(100,0,false);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notification = builder.build();
        manager.notify(20, builder.build());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=100;i++){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    builder.setProgress(100,i,false);
                    manager.notify(20, builder.build());
                }
                builder.setContentText("下载完成");
                manager.notify(20, builder.build());
            }
        }).start();
//        startProgress();
        LogUtils.e("******BinderService onCreate*******");
        super.onCreate();
    }
    public void play(){
        if (!mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.start();
            LogUtils.e("******BinderService play*******");
        }
    }
    public void pause(){
        if (mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.pause();
            LogUtils.e("******BinderService pause*******");
        }
    }
    public void stop(int startId){
        if (mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.stop();
            LogUtils.e("******BinderService stop*******");
        }
        stopSelf(startId);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e("******BinderService onUnbind*******");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.e("******BinderService onDestroy*******");
        super.onDestroy();
    }
    public void startProgress() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NO_3, builder.build());
        builder.setProgress(100, 0, false);
        //下载以及安装线程模拟
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    builder.setProgress(100, i, false);
                    manager.notify(NO_3, builder.build());
                    //下载进度提示
                    builder.setContentText("下载" + i + "%");
                    try {
                        Thread.sleep(50);//演示休眠50毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //下载完成后更改标题以及提示信息
                builder.setContentTitle("开始安装");
                builder.setContentText("安装中...");
                //设置进度为不确定，用于模拟安装
                builder.setProgress(0, 0, true);
                manager.notify(NO_3, builder.build());
                //                manager.cancel(NO_3);//设置关闭通知栏
            }
        }).start();
    }
}
