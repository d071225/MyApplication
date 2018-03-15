package com.dyy.newtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DY on 2018/2/27.
 */

public class MyAnimationView extends View {
    private Paint mPaint;
    private float radius=100f;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public MyAnimationView(Context context) {
        this(context,null);
    }

    public MyAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefault(widthMeasureSpec),getDefault(heightMeasureSpec));
    }
    public int getDefault(int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result=100;
        if (mode==MeasureSpec.EXACTLY||mode==MeasureSpec.AT_MOST){
            result=size;
        }else if (mode==MeasureSpec.UNSPECIFIED){
            result=result;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.drawCircle(300,300,radius,mPaint);
    }
}
