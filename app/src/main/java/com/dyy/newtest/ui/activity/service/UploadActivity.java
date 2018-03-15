package com.dyy.newtest.ui.activity.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.ui.service.UpLoadService;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadActivity extends AppCompatActivity {

    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.tv_upload_status)
    TextView tvUploadStatus;
    private UploadReceiver uploadReceiver;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        intiBroadcastReceiver();
    }
    private void intiBroadcastReceiver() {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("broadcast_upload");
        uploadReceiver = new UploadActivity.UploadReceiver();
        registerReceiver(uploadReceiver,intentFilter);
    }

    @OnClick(R.id.btn_upload)
    public void onClick() {
        Intent intent=new Intent(this, UpLoadService.class);
        path = Environment.getExternalStorageDirectory() + File.separator + "debug.apk";
        intent.putExtra("path", path);
        startService(intent);
        tvUploadStatus.setText(path +" 正在下载...");
    }
    public class UploadReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("--------onReceive-----------");
            if (intent!=null&&intent.getAction().equals("broadcast_upload")){
                String msg = intent.getStringExtra("msg");
                LogUtils.e("--------onReceive-----------msg="+msg);
                tvUploadStatus.setText(msg);
//                File apkFile = new File(path);
//                Intent installIntent = new Intent(Intent.ACTION_VIEW);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
//                startActivity(installIntent);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                installIntent.setDataAndType(Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "debug.apk")),
                        "application/vnd.android.package-archive");
                context.startActivity(installIntent);
            }
        }
    }
}
