package com.dyy.newtest.utils;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.List;

import okhttp3.Cookie;

/**
 * Created by wumengmeng on 2016/9/3/0003.
 */
public class WebViewCookieUtil {
    private static WebViewCookieUtil instance;

    private WebViewCookieUtil() {
    }

    public static WebViewCookieUtil newInstance(Context context) {
        if (instance == null) {
            synchronized (WebViewCookieUtil.class) {
                if (instance == null) {
                    instance = new WebViewCookieUtil();
                }
            }
        }
        return instance;
    }

    public void setCookie(String url) {
//        url="http://test.www.homedo.com";
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        List<Cookie> cookieList = new CustomCookieStore(MyApplacation.getContext()).getCookies();
        cookieManager.removeAllCookie();
        for (int i = 0; i < cookieList.size(); i++) {
            Cookie cookie = cookieList.get(i);
            cookieManager.setCookie(url, cookie.toString());
        }
        cookieManager.setCookie(url, "utime=1440;domain=.homedo.com;path=/");
        cookieManager.setCookie(url, "homedo_port=app;domain=.homedo.com;path=/");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(MyApplacation.getContext());
            CookieSyncManager.getInstance().sync();
        }
        String cookie = cookieManager.getCookie(url);
    }

}
