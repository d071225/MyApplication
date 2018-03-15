package com.dyy.newtest.ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

public class PlayerService extends Service {

    private MediaPlayer mediaPlayer;
    public static final String PLAY="paly";
    public static final String PAUSE="pause";
    public static final String STOP="stop";
    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("******PlayerService onStartCommand*******"+";flags:"+flags+";startId:"+startId);
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            String key= (String) bundle.getSerializable("key");
            LogUtils.e("******PlayerService onStartCommand******* key="+key);
            switch (key){
                case PLAY:
                    play();
                    break;
                case PAUSE:
                    pause();
                    break;
                case STOP:
                    stop(startId);
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
//        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        if (mediaPlayer==null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.i_wanna_go);
            mediaPlayer.setLooping(false);
        }
        LogUtils.e("******PlayerService onCreate*******");
        super.onCreate();
    }
    public void play(){
        if (!mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.start();
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.icon_map);
            builder.setContentTitle("前台service");
            builder.setContentText("这是一个前台service");
            startForeground(1,builder.build());
            LogUtils.e("******PlayerService play*******");
        }
    }
    public void pause(){
        if (mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.pause();
            LogUtils.e("******PlayerService pause*******");
        }
    }
    public void stop(int startId){
        if (mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.stop();
            LogUtils.e("******PlayerService stop*******");
        }
        stopSelf(startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("******PlayerService onDestroy*******");
    }
}
