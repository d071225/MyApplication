package com.dyy.newtest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.retrofitInterface.MyOnClick;
import com.dyy.newtest.retrofitInterface.MyViewInject;
import com.dyy.newtest.utils.ViewInjectTest;

public class ViewInjectActivity extends AppCompatActivity {
    @MyViewInject(R.id.btn_test1)
    Button btn_test1;
    @MyViewInject(R.id.btn_test2)
    Button btn_test2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inject);
        ViewInjectTest.inject(this);
    }
    @MyOnClick({R.id.btn_test1,R.id.btn_test2})
    public void MyOnClick(View view){
        switch (view.getId()){
            case R.id.btn_test1:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("alipays://platformapi/startapp?saId=10000007&qrcode=%68%74%74%70%73%3A%2F%2F%71%72%2E%61%6C%69%70%61%79%2E%63%6F%6D%2F%62%61%78%30%32%33%30%33%38%68%71%73%76%72%33%65%61%65%6E%79%34%30%30%65%3F%5F%73%3D%77%65%62%2D%6F%74%68%65%72"));
//                intent.setData(Uri.parse("http://www.baidu.com"));
                LogUtils.e(intent.getData());
                startActivity(intent);
                break;
            case R.id.btn_test2:
                ToastUtils.showLong("按钮2被点击");
                break;
        }
    }
}
