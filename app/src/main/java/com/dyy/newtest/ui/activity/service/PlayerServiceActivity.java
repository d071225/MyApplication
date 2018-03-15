package com.dyy.newtest.ui.activity.service;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.ui.service.BinderService;
import com.dyy.newtest.ui.service.PlayerService;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerServiceActivity extends AppCompatActivity {

    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_stop)
    Button btnStop;
    private Context mContext;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("-----------onServiceConnected------------");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e("-----------onServiceDisconnected------------");
        }
    };
    private long i = 1;
    private int NO_3=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_service);
        ButterKnife.bind(this);
        mContext = this;

    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                //1.startservice
//                goToPlayerSevice(PlayerService.PLAY);
                //2.binderservice
                Intent intent = new Intent(mContext, BinderService.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", PlayerService.PLAY);
                intent.putExtras(bundle);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                //3.intentservice
//                Intent intent=new Intent(mContext, MyIntentService.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("key","当前值"+ i++);
//                intent.putExtras(bundle);
//                startService(intent);
                //带进度的notification
//                startProgress();
                break;
            case R.id.btn_pause:
                goToPlayerSevice(PlayerService.PAUSE);
                break;
            case R.id.btn_stop:
//                goToPlayerSevice(PlayerService.STOP);
//                unbindService(serviceConnection);
                break;
        }
    }

    private void goToPlayerSevice(Serializable value) {
        Intent intent = new Intent(mContext, PlayerService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", value);
        intent.putExtras(bundle);
        startService(intent);
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
