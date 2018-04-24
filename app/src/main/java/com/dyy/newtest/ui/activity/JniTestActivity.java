package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.jni.AesUtils;
import com.dyy.newtest.jni.JniTest;

public class JniTestActivity extends AppCompatActivity {
    private String aesKey="55C63C2A5D572C4CF638C82105C9905C";
    private String content="123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);
        String jniString = JniTest.getJniString();
        LogUtils.e(jniString);
        LogUtils.e(JniTest.add(5,3));
        String key = AesUtils.generateKeyString("abc");
        String key2 = AesUtils.generateKeyString("abc");
        String key3 = AesUtils.generateKeyString("abc");
        System.out.println(key);
        System.out.println(key2);
        System.out.println(key3);
        String encrypt = AesUtils.encrypt(content, aesKey);
        System.out.println(encrypt);
        String decrypt = AesUtils.decrypt(encrypt, aesKey);
        System.out.println(decrypt);
        String aesEncContent = AesUtils.getAesEncContent(content);
        System.out.println(aesEncContent);
        String aesDecContent = AesUtils.getAesDecContent(aesEncContent);
        System.out.println(aesDecContent);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
