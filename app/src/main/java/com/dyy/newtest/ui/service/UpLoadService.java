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
        LogUtils.e("-------------onHandleIntent-----------");
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
//            boolean hasAlwaysDeniedPermission = PermissionUtils.hasAlwaysDeniedPermission(UploadActivity.mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (hasAlwaysDeniedPermission){
//                LogUtils.e(hasAlwaysDeniedPermission+";"+getPackageName());
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                intent.setData(Uri.parse("package:" + getPackageName()));
//                startActivity(intent);
//            }else {
//                PermissionUtils.requestPermissions(UploadActivity.mContext, 101, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionListener() {
//                    @Override
//                    public void onPermissionGranted() {
//                        ToastUtils.showLong("读取SD授权成功！");
//                    }
//
//                    @Override
//                    public void onPermissionDenied(String[] deniedPermissions) {
//                        ToastUtils.showLong("读取SD授权失败！");
//                    }
//                });
//            }
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
                    if (progress%10==0) {
                        builder.setProgress(100, progress, false);
                        builder.setContentText("下载进度：" + progress + "%");
                        manager.notify(20, builder.build());
                    }
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

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtils.e("-------------onStartCommand-----------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("-------------onCreate-----------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.cancelAll();
        LogUtils.e("-------------onDestroy-----------");
    }
}
