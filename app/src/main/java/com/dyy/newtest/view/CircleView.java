package com.dyy.newtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dyy.newtest.R;

/**
 * Created by DY on 2018/3/1.
 */

public class CircleView extends View {

    private Paint mPaint;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
//        BitmapFactory.Options options=new BitmapFactory.Options();
//        options.inJustDecodeBounds=true;
//        BitmapFactory.decodeResource(getResources(), R.drawable.splash_ship, options);
//        options.inSampleSize=20;
//        options.inJustDecodeBounds=false;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_ship, options);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k2);
//        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, 200, 200);
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int byteCount = bitmap.getByteCount();
        Log.e("123","bitmapHeight:"+bitmapHeight+";bitmapWidth:"+bitmapWidth+";byteCount"+byteCount);
        Rect rect=new Rect(0,0,300,300);
        canvas.drawBitmap(bitmap,0,0,null);
//        BitmapShader bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        mPaint.setShader(bitmapShader);
//        canvas.drawRect(rect,mPaint);
    }
}
