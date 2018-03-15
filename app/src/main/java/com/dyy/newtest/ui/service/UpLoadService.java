package com.dyy.newtest.ui.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DY on 2018/3/15.
 */

public class UpLoadService extends IntentService {

    private InputStream is;
    private FileOutputStream fos;
    private int progress;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpLoadService(String name) {
        super(name);
    }
    public UpLoadService() {
        super("UpLoadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.icon_map);
            builder.setContentTitle("下载");
            builder.setContentText("下载中");
            builder.setProgress(100,0,false);
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(20, builder.build());
            String path = intent.getStringExtra("path");
            upLoadFile(path);
        }
    }
    public void upLoadFile(String path){
        try {
//            Thread.sleep(3000);
            URL url=new URL("http://dl001.liqucn.com/upload/2017/293/g/com.snda.wifilocating_4.2.61_liqucn.com.apk");
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.connect();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode==200) {
                is = con.getInputStream();
                int contentLength = con.getContentLength();
//                int contentLength=is.available();
                LogUtils.e("available:"+contentLength);
                fos = new FileOutputStream(new File(path));
                byte[] bytes=new byte[1024];
                int len=0;
                int contentRead=0;
                while ((len= is.read(bytes))!=-1){
                    contentRead+=len;
                    progress =contentRead*100/contentLength;
                    fos.write(bytes,0,len);
                    fos.flush();
                    builder.setProgress(100,progress,false);
                    builder.setContentText("下载进度："+progress+"%");
                    manager.notify(20,builder.build());
                }
                builder.setProgress(0,0,true);
                builder.setContentText("安装中...");
                builder.setContentTitle("安装");
                manager.notify(20,builder.build());
                Intent intent = new Intent();
                intent.setAction("broadcast_upload");
                intent.putExtra("msg", "下载成功");
                sendBroadcast(intent);
            }
        }  catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is!=null) is.close();
                if (fos!=null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
