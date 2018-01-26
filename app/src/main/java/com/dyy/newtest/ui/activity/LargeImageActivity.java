package com.dyy.newtest.ui.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.dyy.newtest.R;
import com.dyy.newtest.utils.GlideCacheUtil;
import com.dyy.newtest.view.MyGlideModule;
import com.shizhefei.view.largeimage.LargeImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LargeImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_normal)
    ImageView ivNormal;
    @BindView(R.id.iv_large)
    LargeImageView ivLarge;
    @BindView(R.id.iv_normal_to_big)
    ImageView ivNormalToBig;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_save)
    TextView tvSave;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);
        ButterKnife.bind(this);
        GlideCacheUtil glideCacheUtil = GlideCacheUtil.getInstance();
        glideCacheUtil.clearImageAllCache(this);
        url = "http://img.tuku.cn/file_big/201502/3d101a2e6cbd43bc8f395750052c8785.jpg";
        Glide.with(this).load(url).asBitmap().centerCrop().placeholder(R.mipmap.refresh_loading01).override(200, 150).into(ivNormal);
    }

    @OnClick({R.id.iv_normal, R.id.iv_large,R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_normal:
                pb.setVisibility(View.VISIBLE);
                Glide.with(this).load(url).crossFade().thumbnail(0.1f).into(new GlideDrawableImageViewTarget(ivNormalToBig) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        pb.setVisibility(View.GONE);
                        tvSave.setVisibility(View.VISIBLE);
                    }
                });
//                Glide.with(this).load(url).downloadOnly(new SimpleTarget<File>() {
//                    @Override
//                    public void onResourceReady(final File resource, GlideAnimation<? super File> glideAnimation) {
//                        ivLarge.setImage(BitmapFactory.decodeFile(resource.getAbsolutePath()));
//                    }
//                });
                break;
            case R.id.iv_large:
                break;
            case R.id.tv_save:
                saveFile(TimeUtils.getNowString()+"_pic.jpg",LargeImageActivity.this,url);
                break;
        }
    }

    private void saveFile(final String fileName, final Context context, final String url) {
        //方法一：
//        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
//            @Override
//            public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
//                saveFileToSD(fileName,resource,context);
//            }
//        });

        //方法二：
//        new Thread(new Runnable() {
//
//
//                @Override
//                public void run() {
//                    try {
//                    FutureTarget target = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL);
//                    File file = (File) target.get();
//                    saveFileToSD(fileName,file,context);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//        }).start();
    }

    private void saveFileToSD(String fileName, byte[] bytes,Context context) {
//        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            String path = context.getExternalCacheDir().getAbsolutePath() + File.separator + MyGlideModule.CACHE_NAME ;
            File file=new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            try {
                BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(path+File.separator+fileName));
                bos.write(bytes);
                bos.flush();
                bos.close();
                Toast.makeText(context,"图片保存成功,路径为："+path,Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }else {
//            Toast.makeText(context,"SD卡不可用",Toast.LENGTH_SHORT).show();
//        }
    }
    private void saveFileToSD(String fileName, File orFile,Context context) {
//        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            String path = context.getExternalCacheDir().getAbsolutePath() + File.separator + MyGlideModule.CACHE_NAME ;
            File file=new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            try {
                BufferedInputStream bis=new BufferedInputStream(new FileInputStream(orFile));
                BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(path+File.separator+fileName));
                byte[] bytes=new byte[1024];
                int len=0;
                while ((len=bis.read(bytes))!=-1){
                    bos.write(bytes,0,len);
                }
                bos.flush();
                bos.close();
                Toast.makeText(context,"图片保存成功,路径为："+path,Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }else {
//            Toast.makeText(context,"SD卡不可用",Toast.LENGTH_SHORT).show();
//        }
    }

    private class ProgressTarget<String, File> implements Target {
        @Override
        public void onLoadStarted(Drawable placeholder) {

        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onResourceReady(Object resource, GlideAnimation glideAnimation) {

        }

        @Override
        public void onLoadCleared(Drawable placeholder) {

        }

        @Override
        public void getSize(SizeReadyCallback cb) {

        }

        @Override
        public void setRequest(Request request) {

        }

        @Override
        public Request getRequest() {
            return null;
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }
    }
}
