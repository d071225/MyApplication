package com.dyy.newtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by daiyangyang on 2018/8/29.
 */

public class MyScrollView extends ScrollView {
    private onScrollChangedListener onScrollChangedListener;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.e("l="+l+";t="+t+";oldl="+oldl+";oldt="+oldt);
        onScrollChangedListener.onScrollChanged(l,t,oldl,oldt);
    }
    public void setOnScrollChangedListener(onScrollChangedListener onScrollChangedListener){
        this.onScrollChangedListener=onScrollChangedListener;
    }
    public interface onScrollChangedListener{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
