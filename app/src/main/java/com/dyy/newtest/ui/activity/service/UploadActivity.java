package com.dyy.newtest.ui.activity.service;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.ui.service.UpLoadService;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class UploadActivity extends AppCompatActivity {

    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.tv_upload_status)
    TextView tvUploadStatus;
    @BindView(R.id.btn_call)
    Button btnCall;
    private UploadReceiver uploadReceiver;
    private String path;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        intiBroadcastReceiver();
        mContext = this;
    }

    private void intiBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("broadcast_upload");
        uploadReceiver = new UploadReceiver();
        registerReceiver(uploadReceiver, intentFilter);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageNeedsPermission() {
        Intent intent = new Intent(this, UpLoadService.class);
        path = Environment.getExternalStorageDirectory() + File.separator + "debug.apk";
        intent.putExtra("path", path);
        startService(intent);
        tvUploadStatus.setText(path + " 正在下载...");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UploadActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageOnShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(mContext)
                .setMessage("需要读写SD卡权限")
                .setTitle("权限请求")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storagePermissionDenied() {
        new AlertDialog.Builder(mContext)
                .setMessage("您已经拒绝读写SD卡权限，可能会导致应用的某些功能无法使用")
                .setTitle("权限拒绝说明")
                .setPositiveButton("确定", null)
                .show();
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageNeverAskAgain() {
        new AlertDialog.Builder(mContext)
                .setMessage("您已经拒绝读写SD卡权限，请到设置里手动打开")
                .setTitle("权限被禁")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @OnClick({R.id.btn_upload, R.id.btn_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                UploadActivityPermissionsDispatcher.storageNeedsPermissionWithPermissionCheck(this);
                break;
            case R.id.btn_call:

                break;
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callNeedsPermission() {
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void callOnShowRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void callPermissionDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void callNeverAskAgain() {
    }

    public class UploadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("--------onReceive-----------");
            if (intent != null && intent.getAction().equals("broadcast_upload")) {
                String msg = intent.getStringExtra("msg");
                LogUtils.e("--------onReceive-----------msg=" + msg);
                tvUploadStatus.setText(msg);
//                File apkFile = new File(path);
//                Intent installIntent = new Intent(Intent.ACTION_VIEW);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
//                startActivity(installIntent);


//                Intent installIntent = new Intent(Intent.ACTION_VIEW);
//                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                installIntent.setDataAndType(Uri.fromFile(new File(Environment
//                                .getExternalStorageDirectory(), "debug.apk")),
//                        "application/vnd.android.package-archive");
//                context.startActivity(installIntent);

                startActivity(IntentUtils.getInstallAppIntent(new File(path), ("com.dyy.newtest.fileprovider"), true));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploadReceiver != null) {
            unregisterReceiver(uploadReceiver);
        }
    }
}
