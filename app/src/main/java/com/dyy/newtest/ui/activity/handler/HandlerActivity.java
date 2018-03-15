package com.dyy.newtest.ui.activity.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HandlerActivity extends AppCompatActivity {
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private Handler handlerChild = null;
    //    private static Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
////            ToastUtils.showLong("请求成功");
//            LogUtils.e("主线程收到消息:"+msg.obj+"---当前线程："+Thread.currentThread());
//            Toast.makeText(HandlerActivity.this,"主线程收到消息",Toast.LENGTH_SHORT).show();
//        }
//    };
    private Handler handler = new ActivityHandler(HandlerActivity.this);
    private int count=0;

    public static class ActivityHandler extends Handler {
        private WeakReference<Activity> mActivity;

        public ActivityHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerActivity handlerActivity = (HandlerActivity) mActivity.get();
            handlerActivity.handlerToDo(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
//        new AsyncTask<String, Void, String>() {
//            @Override
//            protected String doInBackground(String... strings) {
//                return "你好百度";
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                ToastUtils.showLong(s);
//            }
//        }.execute("www.baidu.com");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder().url("http://www.baidu.com").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.e("当前线程" + Thread.currentThread().getName());
//                        Toast.makeText(HandlerActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
//                handler.sendEmptyMessage(0);

            }
        });
//            }
//        }).start();
    }

    public void handlerToDo(Message msg) {
        LogUtils.e("主线程收到消息:" + msg.obj + "---当前线程：" + Thread.currentThread());
//        Toast.makeText(HandlerActivity.this, "主线程收到消息:" + msg.obj, Toast.LENGTH_SHORT).show();
        count++;
        tvContent.setText(msg.obj+";接受次数:"+count);
    }

    @OnClick({R.id.button8, R.id.button9})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button8:
                new Thread(new childThread()).start();
                break;
            case R.id.button9:
                final Message message = new Message();
                message.obj = "主线程的数据mainThread";
                handler.sendMessageDelayed(message, 10000);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    class childThread implements Runnable {
        @Override
        public void run() {
            Looper.prepare();
            final Message message = new Message();
            message.obj = "子线程的数据childThread";
            handler.sendMessageDelayed(message, 600000);
            handlerChild = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    LogUtils.e("子线程收到数据:" + msg.obj + "---当前线程：" + Thread.currentThread());
                    Toast.makeText(HandlerActivity.this, "子线程收到数据", Toast.LENGTH_SHORT).show();
                }
            };
            Looper.loop();
        }
    }
}
