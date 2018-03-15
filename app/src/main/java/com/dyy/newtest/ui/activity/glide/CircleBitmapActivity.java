package com.dyy.newtest.ui.activity.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleBitmapActivity extends AppCompatActivity {

    @BindView(R.id.iv_circle)
    ImageView ivCircle;
    @BindView(R.id.iv_large)
    LargeImageView ivLarge;
    @BindView(R.id.iv_region)
    ImageView iv_region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_bitmap);
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_ship_xxhdpi);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap copyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        LogUtils.e(copyBitmap.getWidth() + ";" + copyBitmap.getHeight() + ";" + copyBitmap.getByteCount());
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
        Canvas canvas = new Canvas(copyBitmap);
        int min = Math.min(bitmap.getHeight(), bitmap.getWidth());
        canvas.drawCircle(min / 2, min / 2, min / 2, mPaint);
//        canvas.drawRoundRect(new RectF(0,0,bitmap.getWidth(),bitmap.getHeight()),20,100,mPaint);
        ivCircle.setImageBitmap(copyBitmap);

        /**
         * 加载大图
         */
        try {
            InputStream inputStream = getAssets().open("111.jpg");
//            BitmapFactory.Options op=new BitmapFactory.Options();
//            op.inJustDecodeBounds=true;
//            BitmapFactory.decodeStream(inputStream, null, op);
//            LogUtils.e(op.outWidth+";"+op.outHeight);
//            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            Bitmap bitmap1 = bitmapRegionDecoder.decodeRegion(new Rect(0, 0, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()), options);
//            LogUtils.e(bitmap1.getWidth()+";"+bitmap1.getHeight()+";"+bitmap1.getByteCount());
//            iv_region.setImageBitmap(bitmap1);

            ivLarge.setImage(new InputStreamBitmapDecoderFactory(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
