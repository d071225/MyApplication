package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dyy.newtest.R;
import com.dyy.newtest.bean.TestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusActivity extends AppCompatActivity {

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.pb_download)
    ProgressBar pbDownload;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int count = 0;
    private TestEvent testEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        testEvent = new TestEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    count += 15;
                    if (count>100) count=100;
                    testEvent.setCount(count);
                    testEvent.setTitle(count+"/100");
                    EventBus.getDefault().post(testEvent);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void TestEventBus(TestEvent testEvent){
//        pbDownload.setProgress(testEvent.getCount());
//        tvTitle.setText(testEvent.getTitle());
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(TestEvent testEvent) {
          System.out.println("==============update==================");
        pbDownload.setProgress(testEvent.getCount());
        tvTitle.setText(testEvent.getTitle());
    }
}
