package com.dyy.newtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.dyy.newtest.R;

/**
 * Created by DY on 2018/1/23.
 */

public class CircleProgressView extends View{

    private int innerCircleTextSize;
    private int innerCircleTextColor;
    private int innerCircleSize;
    private int innerCircleColor;
    private int outerCircleSize;
    private int outerCircleStrokeWidth;
    private int outerCircleColor;
    private Paint mPaint;
    private int maxLength;
    private String textContent;
    private float outerCircleStartAngle;
    private float circleProgress;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressView, defStyleAttr, 0);
        if (typedArray!=null){
            textContent = typedArray.getString(R.styleable.CircleProgressView_InnerCircleText);
            innerCircleTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_InnerCircleTextSize, SizeUtils.sp2px(16));
            innerCircleTextColor = typedArray.getColor(R.styleable.CircleProgressView_InnerCircleTextColor, Color.WHITE);
            innerCircleSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_InnerCircleSize, SizeUtils.dp2px(20));
            innerCircleColor = typedArray.getColor(R.styleable.CircleProgressView_InnerCircleColor, Color.GRAY);
            outerCircleSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_OuterCircleSize, SizeUtils.dp2px(40));
            outerCircleStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_OuterCircleStrokeWidth, SizeUtils.dp2px(10));
            outerCircleColor = typedArray.getColor(R.styleable.CircleProgressView_OuterCircleColor, Color.GRAY);
            outerCircleStartAngle = typedArray.getFloat(R.styleable.CircleProgressView_OuterCircleStartAngle, -90f);
            circleProgress = typedArray.getFloat(R.styleable.CircleProgressView_CircleProgress, 0);
        }
        typedArray.recycle();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        maxLength = Math.min(width, height);
        LogUtils.a("width:"+width+";height:"+height+";maxLength:"+maxLength);
        setMeasuredDimension(getDefaultSize(widthMeasureSpec),getDefaultSize(heightMeasureSpec));
    }
    public int getDefaultSize(int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result=SizeUtils.dp2px(100);
        if (mode==MeasureSpec.EXACTLY){
            result=size;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtils.aTag("onDraw","------onLayout-------");
//        ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        layoutParams.width=100;
//        layoutParams.height=100;
//        this.setLayoutParams(layoutParams);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int innerCircleRadius = maxLength / 2;
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(Color.BLACK);
//        canvas.drawRect(0,0,maxLength,maxLength,mPaint);

        mPaint.setColor(innerCircleColor);
        if (innerCircleSize>=innerCircleRadius){
           innerCircleSize=innerCircleRadius;
        }
//        LogUtils.a("innerCircleRadius:"+innerCircleRadius+";innerCircleSize:"+innerCircleSize);
        LogUtils.aTag("onDraw","-------------getMeasuredWidth"+getMeasuredWidth()+";getMeasuredHeight"+getMeasuredHeight());
        canvas.drawCircle(innerCircleRadius, innerCircleRadius,innerCircleSize,mPaint);

        if (textContent!=null) {
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setColor(innerCircleTextColor);
            mPaint.setTextSize(innerCircleTextSize);
            Rect rect = new Rect();
            mPaint.getTextBounds(textContent, 0, textContent.length(), rect);
//            LogUtils.a("textContent:" + textContent + ";rect.width():" + rect.width() + ";rect.height()" + rect.height() + ";innerCircleRadius:" + innerCircleRadius);
//            LogUtils.a("getWidth:" + getWidth() + ";getHeight:" + getHeight());
            canvas.drawText(textContent, innerCircleRadius - rect.width() / 2, innerCircleRadius + rect.height() / 2, mPaint);
        }
        mPaint.reset();
        mPaint.setAntiAlias(true);
        RectF rectF=new RectF(maxLength*0.1f,maxLength*0.1f,maxLength*0.9f,maxLength*0.9f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(outerCircleStrokeWidth);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(rectF,outerCircleStartAngle,360,false,mPaint);
        mPaint.setColor(outerCircleColor);
//        LogUtils.a("maxLength:"+maxLength+";outerCircleStartAngle:"+outerCircleStartAngle+";circleProgress:"+circleProgress);
        canvas.drawArc(rectF,outerCircleStartAngle,circleProgress*360/100,false,mPaint);

    }

    public int getInnerCircleTextSize() {
        return innerCircleTextSize;
    }

    public void setInnerCircleTextSize(int innerCircleTextSize) {
        this.innerCircleTextSize = innerCircleTextSize;
    }

    public int getInnerCircleTextColor() {
        return innerCircleTextColor;
    }

    public void setInnerCircleTextColor(int innerCircleTextColor) {
        this.innerCircleTextColor = innerCircleTextColor;
    }

    public int getInnerCircleSize() {
        return innerCircleSize;
    }

    public void setInnerCircleSize(int innerCircleSize) {
        this.innerCircleSize = innerCircleSize;
    }

    public int getInnerCircleColor() {
        return innerCircleColor;
    }

    public void setInnerCircleColor(int innerCircleColor) {
        this.innerCircleColor = innerCircleColor;
    }

    public int getOuterCircleSize() {
        return outerCircleSize;
    }

    public void setOuterCircleSize(int outerCircleSize) {
        this.outerCircleSize = outerCircleSize;
    }

    public int getOuterCircleStrokeWidth() {
        return outerCircleStrokeWidth;
    }

    public void setOuterCircleStrokeWidth(int outerCircleStrokeWidth) {
        this.outerCircleStrokeWidth = outerCircleStrokeWidth;
    }

    public int getOuterCircleColor() {
        return outerCircleColor;
    }

    public void setOuterCircleColor(int outerCircleColor) {
        this.outerCircleColor = outerCircleColor;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public float getOuterCircleStartAngle() {
        return outerCircleStartAngle;
    }

    public void setOuterCircleStartAngle(float outerCircleStartAngle) {
        this.outerCircleStartAngle = outerCircleStartAngle;
    }

    public float getCircleProgress() {
        return circleProgress;
    }

    public void setCircleProgress(float circleProgress) {
        this.circleProgress = circleProgress;
    }
}
