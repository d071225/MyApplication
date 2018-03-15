package com.dyy.newtest.ui.activity.glide;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.dyy.newtest.R;
import com.dyy.newtest.retrofitInterface.ProgressListener;
import com.dyy.newtest.utils.ProgressInterceptor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressGlideActivity extends AppCompatActivity {

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.iv)
    ImageView iv;
    private ProgressDialog progressDialog;
    private String url="http://img.tuku.cn/file_big/201502/3d101a2e6cbd43bc8f395750052c8785.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_glide);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this,R.style.dialog);
    }

    @OnClick({R.id.btn_start, R.id.iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                ProgressInterceptor.addListener(url, new ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        progressDialog.setProgress(progress);
                    }
                });
                Glide.with(ProgressGlideActivity.this).load(url).crossFade().thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.NONE).
                        into(new GlideDrawableImageViewTarget(iv){
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                progressDialog.show();
                            }

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                progressDialog.dismiss();
                                ProgressInterceptor.removeListener(url);
                            }
                        });
                break;
            case R.id.iv:
                break;
        }
    }
}
