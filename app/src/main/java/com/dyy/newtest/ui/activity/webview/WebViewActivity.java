package com.dyy.newtest.ui.activity.webview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.utils.WebViewCookieUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    BridgeWebView webview;
    @BindView(R.id.refresh)
    LinearLayout refresh;
    @BindView(R.id.btn_new)
    Button btnNew;
    @BindView(R.id.btn_old)
    Button btnOld;
    @BindView(R.id.et_goods)
    EditText etGoods;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String url;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new BridgeWebViewClient(webview));
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                LogUtils.e("进度===》"+newProgress);
                if (newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        url = "https://www.uat.homedo.com//bzzx/?port=app";
        refreshH5(url);
//        refresh.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
//            @Override
//            public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
//                if (child.getScrollY() > 0)
//                    return true;
//                return false;
//            }
//        });
//        url = "https://www.homedo.com/Themes/IosApp/guess-like-user.shtml?port=app";
//        url = "https://test-www.homedo.com/mobile/homedobanner/201808081059192288.shtml";
//        url = "https://test-www.homedo.com/mobile/homedobanner/201808081532485892.shtml";
    }

    private void refreshH5(String url) {
        url = url;
        WebViewCookieUtil.newInstance(this).setCookie(url);//给WebView设置Cookie
        webview.registerHandler("registerHDBridgeHandler", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogUtils.e(data);
            }
        });
        webview.loadUrl(url);
    }

    @OnClick({R.id.btn_new, R.id.btn_old})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_new:
//                url = "https://test-www.homedo.com/mobile/homedobanner/201808081059192288.shtml";
                url = "https://www.uat.homedo.com//bzzx/?port=app";
                refreshH5(url);
//                etGoods.setHintTextColor(getResources().getColor(R.color.Blue));
                break;
            case R.id.btn_old:
                url = "https://test-www.homedo.com/mobile/homedobanner/201808081532485892.shtml";
                refreshH5(url);
                break;
        }
    }
}
