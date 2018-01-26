package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.dyy.newtest.R;
import com.dyy.newtest.view.CircleProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleProgressActivity extends AppCompatActivity {

    @BindView(R.id.cpv)
    CircleProgressView cpv;
    @BindView(R.id.btn_start)
    Button btn_start;
    private int progress = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            cpv.setTextContent("进度为：" + progress + "%");
            cpv.setCircleProgress(progress);
            cpv.requestFocus();
            cpv.postInvalidate();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (progress <= 100) {
//                    try {
//                        Thread.sleep(50);
//                        EventBus.getDefault().post(progress);
//                        progress += 1;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(progress);
//                }
//            }
//        }).start();
//        Observable.just(progress)
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer) throws Exception {
//                        while (progress <= 100) {
//                            try {
//                                Thread.sleep(200);
//                                progress += 5;
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        return progress;
//                    }
//                }).subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        cpv.setTextContent("进度为：" + progress + "%");
//                        cpv.setCircleProgress(progress);
//                        cpv.postInvalidate();
//                    }
//                });
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress <= 100) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(progress);
                    progress += 5;
                    System.out.println(progress);
                }
            }
        }).start();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void update(int progress) {

//        cpv.setTextContent("进度为：" + progress + "%");
//        cpv.setCircleProgress(progress);
//        cpv.postInvalidate();
//            tv.setText("进度:"+progress);
//    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void TestEventBus(Integer progress){
//        cpv.setTextContent("进度为：" + progress + "%");
        cpv.setTextContent(progress + "%");
        cpv.setCircleProgress(progress);
        cpv.postInvalidate();
    }
}
