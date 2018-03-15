package com.dyy.newtest.ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {


    private MediaPlayer mediaPlayer;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtils.e("onHandleIntent-------------------");
//        Bundle extras = intent.getExtras();
//        if (extras!=null){
//            for (int i=0;i<10;i++){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                LogUtils.e("onHandleIntent  key="+extras.getSerializable("key"));
//            }
//        }
        mediaPlayer = MediaPlayer.create(this, R.raw.i_wanna_go);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
    }


}
