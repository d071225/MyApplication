package com.dyy.newtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

/**
 * Created by DY on 2018/1/22.
 */

public class MyCustomView extends View {
    int startX;
    int startY;
    private int lastX;
    private int lastY;
    private int lastLeft;
    private int mColor;
    private int mSize;
    private String mContent;
    private Paint paint;

    public MyCustomView(Context context) {
        this(context,null);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCustomView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i=0;i<indexCount;i++){
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.MyCustomView_MyCustomViewColor:
                    mColor = typedArray.getColor(index, Color.BLACK);
                    break;
                case R.styleable.MyCustomView_MyCustomViewSize:
                    mSize=typedArray.getDimensionPixelSize(index,16);
                    break;
                case R.styleable.MyCustomView_MyCustomViewText:
                    mContent=typedArray.getString(index);
                    break;
            }
        }
        typedArray.recycle();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setTextSize(mSize);
        Rect mBounds = new Rect();
        paint.getTextBounds(mContent,0,mContent.length(), mBounds);
        paint.setColor(Color.GREEN);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);

        paint.setColor(mColor);
        canvas.drawText(mContent,getWidth()/2-mBounds.width()/2,getHeight()/2+mBounds.height()/2,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        lastX = (int) event.getRawX();
        lastY = (int) event.getRawY();
        LogUtils.e("onTouchEvent lastX:"+ lastX +";lastY:"+ lastY);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               int downX = (int) event.getRawX();
               int downY = (int) event.getRawY();
                startX= downX;
                startY= downY;
                lastLeft=getLeft();
                LogUtils.e("ACTION_DOWN downX---:"+downX+";downY---:"+downY+";lastLeft():"+lastLeft);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();
                LogUtils.e("ACTION_MOVE moveX:"+moveX+";moveY:"+moveY);
                int offsetX= moveX -startX;
                int offsetY= moveY -startY;
                startX=moveX;
                startY=moveY;
                LogUtils.e("ACTION_MOVE startX:"+startX+";startY:"+startY);
                //方法一：layout移动
                int left = getLeft() + offsetX;
                int top = getTop() + offsetY;
                int right = getRight() + offsetX;
                int bottom = getBottom()+offsetY;
//                if (left<=200){
//                    left=200;
//                }else if (top<=200){
//                    top=200;
//                }else if (right>=ScreenUtils.getScreenWidth()-400){
//                    right=ScreenUtils.getScreenWidth()-400;
//                }else if (bottom>=ScreenUtils.getScreenWidth()-400){
//                    right=ScreenUtils.getScreenWidth()-400;
//                }
//                layout(left, top, right,bottom);
                //方法二：offsetLeftAndRight offsetTopAndBottom
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);
                //方法三：
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                layoutParams.leftMargin=left;
                layoutParams.topMargin=top;
                setLayoutParams(layoutParams);
                LogUtils.e("ACTION_MOVE offsetX===---:"+offsetX+";offsetY===---:"+offsetY);
                break;
            case MotionEvent.ACTION_UP:
//                LogUtils.e("ACTION_UP lastX:"+ lastX +";lastY:"+ lastY+";lastLeft():"+lastLeft);
                LogUtils.e( "getLeft(150px):" + getLeft() + ";getRight(250px):" + getRight() + "\n" + "getTop(125px):" + getTop() + ";getBottom(175px)" + getBottom());
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.a("---------onMeasure--------------");
        setMeasuredDimension(getDefaultSize(widthMeasureSpec),getDefaultSize(heightMeasureSpec));
    }
    public int getDefaultSize(int measureSpec){
        int result=200;
        int mode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                LogUtils.a("---------EXACTLY--------------");
                result=specSize;
                break;
            case MeasureSpec.AT_MOST:
                LogUtils.a("---------AT_MOST--------------");
                break;
            case MeasureSpec.UNSPECIFIED:
                LogUtils.a("---------UNSPECIFIED--------------");
                result=200;
                break;
        }
        return result;
    }
}
