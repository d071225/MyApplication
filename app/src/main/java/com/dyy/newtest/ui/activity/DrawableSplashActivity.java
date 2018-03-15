package com.dyy.newtest.ui.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawableSplashActivity extends AppCompatActivity {

    @BindView(R.id.imageView8)
    ImageView imageView8;
    private int count=1;
//    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_splash);
        ButterKnife.bind(this);
        WindowManager manager = getWindowManager();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        Log.e("123","heightPixels:"+heightPixels+";widthPixels:"+widthPixels);
        getSize(R.drawable.splash_ship);
    }

    @OnClick(R.id.imageView8)
    public void onClick() {
        switch (count%5){
            case 1:
                getSize(R.drawable.splash_ship);
                break;
            case 2:
                getSize(R.drawable.splash_ship_hdpi);
                break;
            case 3:
                getSize(R.drawable.splash_ship_xhdpi);
                break;
            case 4:
                getSize(R.drawable.splash_ship_xxhdpi);
                break;
            case 0:
                getSize(R.drawable.splash_ship_nodpi);
                break;
        }
        count++;
    }

    private  void getSize(int id) {
//        if(bitmap!=null&&!bitmap.isRecycled()){
////            bitmap.recycle();
//            bitmap=null;
//        }
//        System.gc();

        Bitmap bitmap = readBitmapFromResource(getResources(), id, 0, 0);
        imageView8.setImageBitmap(bitmap);
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();
            int byteCount = bitmap.getByteCount();
            Log.e("123","id==="+id+"bitmapHeight:"+bitmapHeight+";bitmapWidth:"+bitmapWidth+";byteCount"+byteCount);

//        imageView8.setImageResource(id);
//        BitmapDrawable drawable = (BitmapDrawable) imageView8.getDrawable();
//        if (drawable!=null){
//            bitmap = drawable.getBitmap();
//            int bitmapHeight = bitmap.getHeight();
//            int bitmapWidth = bitmap.getWidth();
//            int byteCount = bitmap.getByteCount();
//            Log.e("123","id==="+id+"bitmapHeight:"+bitmapHeight+";bitmapWidth:"+bitmapWidth+";byteCount"+byteCount);
//        }

//        imageView8.setImageBitmap(readBitmapFromResource(getResources(),id,0,0));

        getSizePost(id);
    }

    private void getSizePost(int id) {
        imageView8.post(new Runnable() {
            @Override
            public void run() {
                int measuredHeight = imageView8.getMeasuredHeight();
                int measuredWidth = imageView8.getMeasuredWidth();
                int height = imageView8.getHeight();
                int width = imageView8.getWidth();
                Log.e("123","id==="+"measuredHeight:"+measuredHeight+";measuredWidth:"+measuredWidth);
                Log.e("123","id==="+"height:"+height+";width:"+width);
            }
        });
    }

    public Bitmap readBitmapFromResource(Resources resource,int resourceId,int reqWidth,int reqHeight){
        //方法一:
//        InputStream inputStream = resource.openRawResource(resourceId);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=false;
        //方法一:
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        //方法二:
        Bitmap bitmap = BitmapFactory.decodeResource(resource, resourceId, options);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        Log.e("123","resourceId==="+resourceId+"outWidth:"+outWidth+";outHeight:"+outHeight);
        Log.e("123","resourceId==="+resourceId+"height:"+height+";width:"+width);

        //保持图片原始尺寸不变
//        options.inScaled=false;

        options.inSampleSize=2;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;
//        options.inJustDecodeBounds=false;
//        return BitmapFactory.decodeStream(inputStream,null,options);
        return BitmapFactory.decodeResource(resource,resourceId,options);
    }
}
