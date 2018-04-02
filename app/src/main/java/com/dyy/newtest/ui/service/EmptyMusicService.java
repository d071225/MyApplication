package com.dyy.newtest.ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.dyy.newtest.R;

public class EmptyMusicService extends Service {

    private MediaPlayer mediaPlayer;
    private Thread thread;

    public EmptyMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.i_wanna_go);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                playMusic();
            }
        });
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }
    public void playMusic(){
        if (mediaPlayer!=null&&!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }
    public void stopMusic(){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
