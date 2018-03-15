package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawableActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView9)
    ImageView imageView9;
    @BindView(R.id.imageView10)
    ImageView imageView10;
    @BindView(R.id.imageView11)
    ImageView imageView11;
    @BindView(R.id.imageView12)
    ImageView imageView12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.imageView, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6,
            R.id.imageView7,R.id.imageView9,R.id.imageView10,R.id.imageView11,R.id.imageView12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                int measuredWidth = imageView.getMeasuredWidth();
                int measuredHeight = imageView.getMeasuredHeight();
                Log.e("123", "measuredWidth:" + measuredWidth + ";measuredHeight:" + measuredHeight);
                break;
            case R.id.imageView2:
                int measuredWidth2 = imageView2.getMeasuredWidth();
                int measuredHeight2 = imageView2.getMeasuredHeight();
                Log.e("123", "measuredWidth2:" + measuredWidth2 + ";measuredHeight:" + measuredHeight2);
                break;
            case R.id.imageView3:
                int measuredWidth3 = imageView3.getMeasuredWidth();
                int measuredHeight3 = imageView3.getMeasuredHeight();
                Log.e("123", "measuredWidth3:" + measuredWidth3 + ";measuredHeight3:" + measuredHeight3);
                break;
            case R.id.imageView4:
                int measuredWidth4 = imageView4.getMeasuredWidth();
                int measuredHeight4 = imageView4.getMeasuredHeight();
                Log.e("123", "measuredWidth4:" + measuredWidth4 + ";measuredHeight4:" + measuredHeight4);
                break;
            case R.id.imageView5:
                int measuredWidth5 = imageView5.getMeasuredWidth();
                int measuredHeight5 = imageView5.getMeasuredHeight();
                Log.e("123", "measuredWidth5:" + measuredWidth5 + ";measuredHeight5:" + measuredHeight5);
                break;
            case R.id.imageView6:
                int measuredWidth6 = imageView6.getMeasuredWidth();
                int measuredHeight6 = imageView6.getMeasuredHeight();
                Log.e("123", "measuredWidth6:" + measuredWidth6 + ";measuredHeight6:" + measuredHeight6);
                break;
            case R.id.imageView7:
                int measuredWidth7 = imageView7.getMeasuredWidth();
                int measuredHeight7 = imageView7.getMeasuredHeight();
                Log.e("123", "measuredWidth7:" + measuredWidth7 + ";measuredHeight7:" + measuredHeight7);
                break;
            case R.id.imageView9:
                int measuredWidth9 = imageView9.getMeasuredWidth();
                int measuredHeight9 = imageView9.getMeasuredHeight();
                Log.e("123", "measuredWidth9:" + measuredWidth9 + ";measuredHeight9:" + measuredHeight9);
                break;
            case R.id.imageView10:
                int measuredWidth10 = imageView10.getMeasuredWidth();
                int measuredHeight10 = imageView10.getMeasuredHeight();
                Log.e("123", "measuredWidth10:" + measuredWidth10 + ";measuredHeight10:" + measuredHeight10);
                break;
            case R.id.imageView11:
                int measuredWidth11 = imageView11.getMeasuredWidth();
                int measuredHeight11 = imageView11.getMeasuredHeight();
                Log.e("123", "measuredWidth11:" + measuredWidth11 + ";measuredHeight11:" + measuredHeight11);
                break;
            case R.id.imageView12:
                int measuredWidth12 = imageView12.getMeasuredWidth();
                int measuredHeight12 = imageView12.getMeasuredHeight();
                Log.e("123", "measuredWidth12:" + measuredWidth12 + ";measuredHeight7:" + measuredHeight12);
                break;
        }
    }
}
