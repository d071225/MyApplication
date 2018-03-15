package com.dyy.newtest.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dyy.newtest.R;
import com.dyy.newtest.view.EmptyView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ErrorNoDataActivity extends AppCompatActivity {

    @BindView(R.id.btn_loading)
    Button btnLoading;
    @BindView(R.id.img_net)
    ImageView imgNet;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ev)
    EmptyView ev;
    private Context mContext;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap resource = (Bitmap) msg.obj;
            imgNet.setImageBitmap(resource);
            ev.setNoDataImg(resource);
            ev.setNoDataContent("没有数据，点击重新加载");
            ev.setType(EmptyView.NODATA);
        }
    };
    private String url="http://img.tuku.cn/file_big/201502/3d101a2e6cbd43bc8f395750052c8785.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_no_data);
        ButterKnife.bind(this);
        mContext=this;
        ev.setVisibility(View.GONE);
        ev.setEmptyOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ev.setType(EmptyView.NETWORK_LOADING);
                getImg();
            }
        });
    }

    @OnClick(R.id.btn_loading)
    public void onClick() {
       getImg();
    }
    public void getImg(){
        ev.setVisibility(View.VISIBLE);
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ev.setErrorContent("网络错误，请重新加载图片");
                ev.setType(EmptyView.NETWORK_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                LogUtils.e(response.toString());
                InputStream inputStream = body.byteStream();
                byte[] bytes = inputStreamToBytesArr(inputStream);
                BitmapFactory.Options options=new BitmapFactory.Options();
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                LogUtils.e("网络请求解析的bitmap   "+bitmap.getWidth()+";"+bitmap.getHeight()+";"+bitmap.getByteCount());
//                Message message=new Message();
//                message.obj=bitmap;
//                handler.sendMessage(message);
                new Handler(mContext.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(ErrorNoDataActivity.this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                LogUtils.e(resource.getWidth()+";"+resource.getHeight()+";"+resource.getByteCount());
                                ev.setNoDataImg(resource);
                                ev.setNoDataContent("没有数据，点击重新加载");
                                ev.setType(EmptyView.NODATA);
                            }
                        });
                    }
                });
//                Glide.with(ErrorNoDataActivity.this).load(bytes).asBitmap().into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        Message message=new Message();
//                        message.obj=resource;
//                        handler.sendMessage(message);
//                    }
//                });
            }
        });
    }
    public byte[] inputStreamToBytesArr(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] bytes=new byte[1024];
        int len=0;
        while ((len=inputStream.read(bytes))!=-1){
            baos.write(bytes,0,bytes.length);
        }
        inputStream.close();
        baos.close();
        return baos.toByteArray();
    }
}
