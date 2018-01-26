package com.dyy.newtest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * Created by DY on 2018/1/22.
 */

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        this(context,null);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        Log.e("ri", "-----onMeasure-------");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left=0;
        int childCount = getChildCount();
        Log.e("ri", childCount + "------onLayout-------");
        for (int i=0;i<childCount;i++){
            View childAt = getChildAt(i);
            int top= ScreenUtils.getScreenHeight()/2-childAt.getHeight()/2;
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            childAt.layout(left,top,left+ measuredWidth,top+measuredHeight);
            left+=measuredWidth;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
