package com.dyy.newtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dyy.newtest.R;

/**
 * Created by DY on 2018/3/8.
 */

public class MatrixView extends View {

    private Paint mPaint;

    public MatrixView(Context context) {
        this(context,null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Matrix matrix=new Matrix();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_toolbox);
//        canvas.drawBitmap(bitmap,matrix,mPaint);
//        matrix.postScale(2,2);
//        canvas.drawBitmap(bitmap,matrix,mPaint);
//        matrix.reset();
//        matrix.preTranslate(0,bitmap.getHeight());
//        canvas.drawBitmap(bitmap,matrix,mPaint);
        matrix.reset();
        matrix.postRotate(180);
        matrix.postTranslate(bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,matrix,mPaint);
    }
}
