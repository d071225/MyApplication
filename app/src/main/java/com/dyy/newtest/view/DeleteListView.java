package com.dyy.newtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

/**
 * Created by DY on 2018/2/28.
 */

public class DeleteListView extends ListView implements GestureDetector.OnGestureListener,View.OnTouchListener{
    private boolean isShowDeleteBtn;
    private Context mContext;
    private int currentPosition;
    private ViewGroup mlistviewItem;
    private View delteBtn;
    private GestureDetector mGestureDetector;

    public boolean isShowDeleteBtn() {
        return isShowDeleteBtn;
    }

    public void setShowDeleteBtn(boolean showDeleteBtn) {
        isShowDeleteBtn = showDeleteBtn;
    }

    public DeleteListView(Context context) {
        this(context,null);
    }

    public DeleteListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        mGestureDetector = new GestureDetector(mContext,this);
        setOnTouchListener(this);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        if (!isShowDeleteBtn) {
            currentPosition = pointToPosition((int) e.getX(), (int) e.getY());
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!isShowDeleteBtn&&Math.abs(velocityX)>Math.abs(velocityY)){
            delteBtn = LayoutInflater.from(mContext).inflate(R.layout.delete_listitem, null);
            LogUtils.e("currentPosition:"+currentPosition+";getFirstVisiblePosition:"+getFirstVisiblePosition());
            mlistviewItem = (ViewGroup)getChildAt(currentPosition - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            mlistviewItem.addView(delteBtn,params);
            isShowDeleteBtn=true;
            delteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delteBtn !=null) {
                        mlistviewItem.removeView(delteBtn);
                    }
                    delteBtn =null;
                    isShowDeleteBtn=false;
                    onDeleteItemListener.doDeleteItem(currentPosition);
                }
            });
        }
        return false;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return mGestureDetector.onTouchEvent(ev);
//    }
    public void setOnDeleteItemListener(OnDeleteItemListener onDeleteItemListener){
            this.onDeleteItemListener=onDeleteItemListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LogUtils.e("listview setOnTouchListener onTouch");
        if (isShowDeleteBtn){
            hiddenDeleteBtn();
        }
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtils.e("listview onTouchEvent");
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.e("listview dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.e("listview onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    public void hiddenDeleteBtn() {
        if (delteBtn!=null) {
            mlistviewItem.removeView(delteBtn);
            delteBtn = null;
            isShowDeleteBtn = false;
        }
    }

    public  interface OnDeleteItemListener{
           void doDeleteItem(int position);
    }
    public  OnDeleteItemListener onDeleteItemListener;
}
