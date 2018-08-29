package com.dyy.newtest.ui.activity.AppUpdate;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.dyy.newtest.R;
import com.dyy.newtest.bean.User;
import com.dyy.newtest.utils.DownLoadManager;
import com.dyy.newtest.utils.MyApplacation;
import com.dyy.newtest.utils.ObjectUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadManagerActivity extends AppCompatActivity {

    public static final String HOMEDO_URL = "http://180.153.105.142/dd.myapp.com/16891/902EF6936AA181B73A51697890E8E1B2.apk?mkey=5b5ead6be246d181&f=9f22&c=0&fsname=com.homedo.homedoapp_1.6.2_57.apk&p=.apk";
    @BindView(R.id.btn_download_manager)
    Button btnDownloadManager;
    @BindView(R.id.btn_download)
    Button btnDownload;
    private DownloadManager downloadManager;
    private long id;
    private DownloadReceiver mReceiver;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        ButterKnife.bind(this);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mReceiver = new DownloadReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(mReceiver, filter);
        mContext = this;
        File file = new File(DownLoadManager.getAPKDownloadDra(MyApplacation.mContext), "homedo.apk");
        String path = file.getPath();
        String absolutePath = file.getAbsolutePath();
        Uri parse = Uri.parse("file:///storage/emulated/0/Download/homedo.apk");
        String path1 = parse.getPath();
        File file1=new File(path1);
    }
    @OnClick({R.id.btn_download_manager, R.id.btn_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_download_manager:
//                downloadManager();
//                Intent intent= new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse("https://sh-v5.ntalker.com/richtext/Read?id=ed944de024b07d3ee3bf44e4b452b9af");
//                intent.setData(content_url);
//                startActivity(intent);

                User user=new User();
                user.setName("abc");
                user.setAge(25);
                user.setPassword("123456");
                ObjectUtils.saveObject(mContext,user,"user");
                break;
            case R.id.btn_download:
//                download();
                ObjectUtils.deletefile(mContext,"user");
                break;
        }
    }

    private void download() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressNumberFormat("%1d kb /%2d kb");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(HOMEDO_URL, pd);
                    install(mContext,file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void downloadManager() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(HOMEDO_URL));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("河姆渡下载中...");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "homedo.apk");
        request.setVisibleInDownloadsUi(true);
        id = downloadManager.enqueue(request);
    }

    /**
     * 安装apk
     *
     * @param file
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public static void install(Context context,File file) {
//        File file = new File(
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                , "myApp.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.dyy.newtest.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
    public class DownloadReceiver extends BroadcastReceiver {
        public DownloadReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(id));
                c.moveToFirst();
                String path = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                install(context, new File(Uri.parse(path).getPath()));

            }
        }

//        protected void installApk(Context context, File file) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            // 由于没有在Activity环境下启动Activity,设置下面的标签
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
//                //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//                Uri apkUri = FileProvider.getUriForFile(context, "com.dyy.newtest.fileprovider",file);
//                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            }else{
//                intent.setDataAndType(Uri.parse(path),
//                        "application/vnd.android.package-archive");
//            }
//            context.startActivity(intent);
//        }
    }
}
