package com.dyy.newtest.ui.activity.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.Button;
import android.widget.RemoteViews;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.btn_create)
    Button btnCreate;
    private NotificationCompat.Builder builder;
    private RemoteViews remoteViews;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_create)
    public void onClick() {
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon_map);
        //1.常规布局
//        builder.setContentTitle("通知");
//        builder.setContentText("我是一个通知！！！");
//        builder.setTicker("上海 多云 23℃");
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon_toolbox));
//        builder.setNumber(6);
//        builder.setOngoing(true);
        //自定义布局
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.notifi_item);
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("key","play");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,intent , PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.btn_notifi_play,pendingIntent);
        builder.setContent(remoteViews);


        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String key = intent.getStringExtra("key");
        LogUtils.e("------------onNewIntent----------------"+ key);
        ToastUtils.showLong(key);
        if (key.equals("play")) {
//            NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
//            builder.setSmallIcon(R.drawable.icon_map);
//            RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notifi_item);
            Intent intent1 = new Intent(this, NotificationActivity.class);
            intent1.putExtra("key","第二次弹出消息");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
            remoteViews.setTextViewText(R.id.btn_notifi_play,"暂停");
            remoteViews.setTextColor(R.id.btn_notifi_play, Color.RED);
            remoteViews.setOnClickPendingIntent(R.id.btn_notifi_play, pendingIntent);
//            builder.setContent(remoteViews);
            manager.notify(1, builder.build());
        }else {

        }
    }
}
