package com.dyy.newtest.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

public class ForegroundService extends Service {

    private MediaPlayer mediaPlayer;
    public static final String PLAY="paly";
    public static final String PAUSE="pause";
    public static final String STOP="stop";
    private NotificationCompat.Builder builder;
    private RemoteViews remoteViews;
    private PendingIntent playIntent;
    private PendingIntent pauseIntent;
    private PendingIntent stopIntent;
    private NotificationManager nm;
    private Notification notification;
    private int startId;
    private MusicReceiver musicReceiver;

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        LogUtils.e("******PlayerService onStartCommand*******"+";flags:"+flags+";startId:"+ startId);
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
        LogUtils.e("******PlayerService onCreate*******");
        if (mediaPlayer==null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.i_wanna_go);
            mediaPlayer.setLooping(false);
        }
        musicReceiver = new MusicReceiver();
        IntentFilter inflater=new IntentFilter();
        inflater.addAction(PLAY);
        inflater.addAction(STOP);
        registerReceiver(musicReceiver,inflater);
        builder = new NotificationCompat.Builder(this);
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.notifi_item);
//        builder.setSmallIcon(R.drawable.icon_map);
//        builder.setContentTitle("服务通知");
//        builder.setContentText("服务通知*****");
        builder.setContent(remoteViews);
        playIntent = PendingIntent.getBroadcast(this, 1, new Intent(PLAY), PendingIntent.FLAG_CANCEL_CURRENT);
//        pauseIntent = PendingIntent.getBroadcast(this, 2,  new Intent(PAUSE), PendingIntent.FLAG_CANCEL_CURRENT);
        stopIntent = PendingIntent.getBroadcast(this, 3,  new Intent(STOP), PendingIntent.FLAG_CANCEL_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.btn_notifi_play,playIntent);
        remoteViews.setOnClickPendingIntent(R.id.btn_notifi_stop, stopIntent);
        notification = builder.build();
//        notification.defaults=NotificationCompat.DEFAULT_SOUND;
//        nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
//        nm.notify(10,notification);
//        notification.contentView=remoteViews;
        startForeground(10, notification);
        super.onCreate();
    }
    public void play(){
        if (!mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.start();
            remoteViews.setTextViewText(R.id.btn_notifi_play,"暂停");
            remoteViews.setImageViewResource(R.id.img_notifi,R.drawable.icon_map);
//            nm.notify(10,notification);
            startForeground(10, notification);
            LogUtils.e("******PlayerService play*******");
        }
    }
    public void pause(){
        if (mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.pause();
            remoteViews.setTextViewText(R.id.btn_notifi_play,"开始");
            remoteViews.setImageViewResource(R.id.img_notifi,R.mipmap.ic_launcher_round);
//            nm.notify(10,notification);
            startForeground(10, notification);
            LogUtils.e("******PlayerService pause*******");
        }
    }
    public void stop(int startId){
        if (mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.stop();
            nm.cancelAll();
            LogUtils.e("******PlayerService stop*******");
        }
        stopSelf(startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("******PlayerService onDestroy*******");
        if(musicReceiver!=null){
            unregisterReceiver(musicReceiver);
        }
        stopForeground(true);
    }
    public class MusicReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.e("MusicReceiver action="+action);
            switch (action){
                case PLAY:
                    if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                        pause();
                    }else if (!mediaPlayer.isPlaying()){
                        play();
                    }
                    break;
                case PAUSE:
                   play();
                    break;
                case STOP:
                   stop(startId);
                    break;

            }
        }
    }
}
