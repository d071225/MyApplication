package com.dyy.newtest.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.ui.service.ForegroundService;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForegroundActivity extends AppCompatActivity {

    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_stop)
    Button btnStop;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground);
        ButterKnife.bind(this);
        mContext=this;

    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                goToPlayerSevice(ForegroundService.PLAY);
                break;
            case R.id.btn_pause:
                goToPlayerSevice(ForegroundService.PAUSE);
                break;
            case R.id.btn_stop:
                goToPlayerSevice(ForegroundService.STOP);
                break;
        }
    }

    private void goToPlayerSevice(Serializable value) {
        Intent intent=new Intent(mContext, ForegroundService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("key",value);
        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.e("******ForegroundActivity onNewIntent*******");
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            String key= (String) bundle.getSerializable("key");
            switch (key){
                case ForegroundService.PLAY:
                    goToPlayerSevice(ForegroundService.PLAY);
                    break;
                case ForegroundService.PAUSE:
                    goToPlayerSevice(ForegroundService.PAUSE);
                    break;
                case ForegroundService.STOP:
                    goToPlayerSevice(ForegroundService.STOP);
                    break;
            }
        }
    }
}
