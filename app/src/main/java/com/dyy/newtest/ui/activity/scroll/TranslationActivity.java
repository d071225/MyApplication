package com.dyy.newtest.ui.activity.scroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dyy.newtest.R;
import com.dyy.newtest.view.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TranslationActivity extends AppCompatActivity {

    @BindView(R.id.sv)
    MyScrollView sv;
    @BindView(R.id.service_online_iv)
    ImageView serviceOnlineIv;
    @BindView(R.id.back_to_top_iv)
    ImageView backToTopIv;
    private int startT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_view);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.service_online_icon).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(serviceOnlineIv);
        LogUtils.e("屏幕宽=" + ScreenUtils.getScreenWidth() + ";屏幕高=" + ScreenUtils.getScreenHeight());
        sv.setOnScrollChangedListener(new MyScrollView.onScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                int dp_45 = SizeUtils.dp2px(45);
                int dp_65 = SizeUtils.dp2px(65);
                if (t<=0) {
                    backToTopIv.setVisibility(View.GONE);
                    serviceOnlineIv.setTranslationY(-t);
                }
                if (t > 0 && t <= dp_65) {
                    serviceOnlineIv.setTranslationY(-t);
                    if (t > dp_45) {
                        backToTopIv.setVisibility(View.VISIBLE);
                        backToTopIv.setAlpha(t*255 / dp_45);
                    }else if (t>0 && t<= dp_45){
                        backToTopIv.setVisibility(View.GONE);
                    }
                }else if (t > dp_65){
                    serviceOnlineIv.setTranslationY(-dp_65);
                    backToTopIv.setVisibility(View.VISIBLE);
                    backToTopIv.setAlpha(255);
                }
            }
        });
    }

    @OnClick({R.id.service_online_iv, R.id.back_to_top_iv, R.id.tv_first})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service_online_iv:
                ToastUtils.showLong("我是客服");
                break;
            case R.id.back_to_top_iv:
                ToastUtils.showLong("返回顶部");
                break;
            case R.id.tv_first:
                startActivity(new Intent(this,ScrollToOrByActivity.class));
                break;
        }
    }
}
