package com.dyy.newtest.ui.activity.service.keepliveservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dyy.newtest.R;
import com.dyy.newtest.ui.service.LocalService;
import com.dyy.newtest.ui.service.RemoteService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.btn_set)
    Button btnSet;
    @BindView(R.id.btn_dobble)
    Button btnDobble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_set, R.id.btn_dobble})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set:
                AlarmManager am= (AlarmManager) getSystemService(ALARM_SERVICE);
                PendingIntent pi=PendingIntent.getBroadcast(this,0,new Intent("alarm"),PendingIntent.FLAG_UPDATE_CURRENT);
//                am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),60*1000,pi);
                am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),10*1000,pi);
                break;
            case R.id.btn_dobble:
                startService(new Intent(this, LocalService.class));
                startService(new Intent(this, RemoteService.class));
                break;
        }
    }
}
