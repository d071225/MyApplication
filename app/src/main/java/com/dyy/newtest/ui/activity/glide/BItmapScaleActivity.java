package com.dyy.newtest.ui.activity.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BItmapScaleActivity extends AppCompatActivity {

    @BindView(R.id.imageView14)
    ImageView imageView14;
    @BindView(R.id.btn_add)
    Button btnAdd;
    private String url;
    private Bitmap bitmap1;
    private LruCache<String,Bitmap> lruCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_scale);
        ButterKnife.bind(this);
        url = "http://img.tuku.cn/file_big/201502/3d101a2e6cbd43bc8f395750052c8785.jpg";
        imageView14.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                int height1 = imageView14.getHeight();
                int width1 = imageView14.getWidth();
                LogUtils.e(height1 + ";" + width1);
            }
        });
        int maxSize= (int) (Runtime.getRuntime().maxMemory()/1024/8);
        LogUtils.e("内存二分之一大小:"+maxSize);
        lruCache=new LruCache<>(maxSize);
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url1 = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                    conn.setRequestMethod("GET");
//                    bitmap1 = BitmapFactory.decodeStream(conn.getInputStream());
//                    int height = bitmap1.getHeight();
//                    int width = bitmap1.getWidth();
//                    LogUtils.e(height + ";" + width+";"+ bitmap1.getByteCount());

                    byte[] bytes = inputStream2ByteArr(conn.getInputStream());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                    int calculateSize = calculateSize(options, 400, 300);
                    options.inSampleSize = calculateSize;
                    options.inJustDecodeBounds = false;
                    final Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                    int height1 = bitmap1.getHeight();
                    int width1 = bitmap1.getWidth();
                    LogUtils.e(height1 + ";" + width1 + ";" + bitmap1.getByteCount() + ";" + calculateSize);
                    if (bitmap1!=null){
                        lruCache.put(url,bitmap1);
                    }
                    //磁盘压缩图片
//                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,10,baos);
//                    byte[] bytes = baos.toByteArray();
//                    final Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    int height2 = bitmap1.getHeight();
//                    int width2 = bitmap1.getWidth();
//                    LogUtils.e("压缩后====》"+height2 + ";" + width2+";"+bitmap.getByteCount());
                    BItmapScaleActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView14.setImageBitmap(bitmap1);
                            //wrap_content为0，动态设置图片后获取imageView14宽高
                            imageView14.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                                @Override
                                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                                    int height1 = right - left;
                                    int width1 = bottom - top;
                                    LogUtils.e(height1 + ";" + width1);
                                }
                            });
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int calculateSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;
        if (outHeight > reqHeight || outWidth > reqWidth) {
            if (outHeight > outWidth) {
                inSampleSize = outHeight / reqHeight;
            } else {
                inSampleSize = outWidth / reqWidth;
            }
        }
        return inSampleSize;
    }

    private byte[] inputStream2ByteArr(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

    @OnClick(R.id.btn_add)
    public void onClick() {
//        if (bitmap1!=null){
//            bitmap1.recycle();
//            bitmap1=null;
//            System.gc();
//        }
        Bitmap bitmap = lruCache.get(url);
        if (bitmap !=null){
            LogUtils.e("bitmap不为空，从缓存里取");
            imageView14.setImageBitmap(bitmap);
        }else {
            LogUtils.e("bitmap为空，从网络里取");
            getData();
        }
//        Glide.with(BItmapScaleActivity.this).load(url).into(imageView14);
    }
}
