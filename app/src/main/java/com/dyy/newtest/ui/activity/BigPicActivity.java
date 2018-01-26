package com.dyy.newtest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BigPicActivity extends Activity {

    @BindView(R.id.iv_big)
    ImageView ivBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String url = (String) extras.get("url");
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivBig);
    }

    @OnClick(R.id.iv_big)
    public void onViewClicked() {
    }
}
