package com.dyy.newtest.ui.activity.webview;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.ui.activity.service.UploadActivity;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JSAndroidActivity extends AppCompatActivity {

    @BindView(R.id.btn_android_to_js)
    Button btnAndroidToJs;
    @BindView(R.id.wv)
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsandroid);
        ButterKnife.bind(this);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }
        wv.loadUrl("file:///android_asset/javascript.html");
        final Intent intent=new Intent();
        ComponentName componentName=new ComponentName("com.dyy.newtest","com.dyy.newtest.ui.activity.service.UploadActivity");
        intent.setComponent(componentName);
        String toUri = intent.toUri(Intent.URI_INTENT_SCHEME);
        LogUtils.e(toUri);
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(JSAndroidActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                LogUtils.e(url+";"+message);
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("prompt")){
                    String num = uri.getQueryParameter("num");
                    result.confirm("android得到的值返回"+num);
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
//        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new StartActivity(),"startActivity");
        wv.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                LogUtils.e("shouldOverrideUrlLoading(WebView view, WebResourceRequest request)-->"+request);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    String url = request.getUrl().toString();
//                    LogUtils.e("url-->"+url);
//                }
//                return super.shouldOverrideUrlLoading(view, request);
//            }

            /**
             * 1.自定义协议
             * @param view
             * @param url
             * @return
             */
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtils.e("shouldOverrideUrlLoading(WebView view, String url)-->"+url);
//                Uri uri=Uri.parse(url);
//                if (uri.getScheme().equals("ss")){
//                    LogUtils.e(uri.getAuthority()+";"+uri.getHost()+";"+uri.getPath()+";"+uri.getQuery());
//                    return true;
//                }
//                return super.shouldOverrideUrlLoading(view,url);
//            }
            /**
             * 2.自定义协议启动应用组件
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e("shouldOverrideUrlLoading(WebView view, String url)-->"+url);
                try {
                    String decode = URLDecoder.decode(url, "UTF-8");
                    LogUtils.e("decode-->"+decode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (url.startsWith("intent:")||url.startsWith("hello:")){
//                    toActivity(url,Intent.URI_INTENT_SCHEME);
//                    Intent intent1=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//                    startActivity(intent1);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view,url);
            }
        });
    }
    public void toActivity(String uri,int flag){
        try {
            startActivity(Intent.parseUri(uri,flag));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.btn_android_to_js)
    public void onClick() {
//        wv.loadUrl("javascript:callJS()");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wv.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    LogUtils.e("value:"+value);
                }
            });
        }
    }
    public class StartActivity{
        @JavascriptInterface
        public void toUpLoadActivity(){
            Intent intent=new Intent(JSAndroidActivity.this, UploadActivity.class);
            startActivity(intent);
        }
    }
}
