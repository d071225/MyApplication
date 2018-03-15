package com.dyy.newtest.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewActivity extends AppCompatActivity {

    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.imageView13)
    ImageView imageView13;
    @BindView(R.id.tv)
    TextView tv;
    private VelocityTracker velocityTracker;
    private GestureDetector gestureDetector;
    final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 10;
    private AnimatorSet animatorSet;
    private int count;
    private Scroller scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        int scaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        System.out.println("scaledTouchSlop:" + scaledTouchSlop);
        scroller = new Scroller(this);
//        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent e) {
//                LogUtils.e("OnGestureListener 按下屏幕触发onDown");
//                return true;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent e) {
//                LogUtils.e("OnGestureListener 按下屏幕超过100ms触发onShowPress");
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                LogUtils.e("OnGestureListener 单击屏幕触发onSingleTapUp");
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                LogUtils.e("OnGestureListener x轴滑动距离:"+(e2.getX()-e1.getX())+";distanceX"+distanceX);
//                LogUtils.e("OnGestureListener y轴滑动距离:"+(e2.getY()-e1.getY())+";distanceY"+distanceY);
//                return false;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//                LogUtils.e("OnGestureListener 长按屏幕超触发onLongPress");
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                LogUtils.e("OnGestureListener x轴抛物距离:"+(e2.getX()-e1.getX())+";velocityX"+velocityX);
//                LogUtils.e("OnGestureListener y轴抛物距离:"+(e2.getY()-e1.getY())+";velocityY"+velocityY);
//                return false;
//            }
//        });
//        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                LogUtils.e("OnGestureListener 严格的单击屏幕触发onSingleTapConfirmed");
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                LogUtils.e("OnGestureListener 双击屏幕触发onDoubleTap");
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                return false;
//            }
//        });
        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtils.e("e1.getX()-e2.getX()=" + (e1.getX() - e2.getX()));
                LogUtils.e("velocityX=" + velocityX + ";velocityY=" + velocityY);
                if ((e1.getX() - e2.getX()) > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    ToastUtils.showLong("向左滑动");
                } else if ((e2.getX() - e1.getX()) > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    ToastUtils.showLong("向右滑动");
                }
                return true;
            }
        });
        button5.setText("view动画平移100个像素");
        button6.setText("属性动画平移100个像素");
        animatorSet = new AnimatorSet();
        imageView13.setOnTouchListener(new View.OnTouchListener() {

            private int lastY;
            private int lastX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x= (int) event.getRawX();
                int y= (int) event.getRawY();
                int x1= (int) event.getX();
                int y1= (int) event.getY();
                LogUtils.e("x="+x+";y="+y);
//                LogUtils.e("x1="+x1+";y1="+y1);
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int translationX=x-lastX;
                        int translationY=y-lastY;
//                        LogUtils.e("x="+x+";y="+y);
//                        LogUtils.e("lastX="+lastX+";lastX="+lastX);
//                        LogUtils.e("translationX="+translationX+";translationY="+translationY);
//                        animatorSet.playTogether(ObjectAnimator.ofFloat(imageView13,"translationX",translationX,translationX),
//                                ObjectAnimator.ofFloat(imageView13,"translationY",translationY,translationY));
//                        animatorSet.start();
//                        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) imageView13.getLayoutParams();
//                        layoutParams.leftMargin=imageView13.getLeft()+translationX;
//                        layoutParams.topMargin=translationY;
//                        imageView13.setLayoutParams(layoutParams);
                        int left=imageView13.getLeft()+translationX;
                        int right=imageView13.getRight()+translationX;
                        int top=imageView13.getTop()+translationY;
                        int bottom=imageView13.getBottom()+translationY;
                        if (left<0) {
                            left=0;
                            right=imageView13.getWidth();
                        }
                        if (right> ScreenUtils.getScreenWidth()){
                            left=ScreenUtils.getScreenWidth()-imageView13.getWidth();
                            right=ScreenUtils.getScreenWidth();
                        }
                        if (top<0) {
                            top=0;
                            bottom=imageView13.getHeight();
                        }
                        if (bottom> ScreenUtils.getScreenHeight()){
                            left=ScreenUtils.getScreenHeight()-imageView13.getHeight();
                            right=ScreenUtils.getScreenHeight();
                        }
                        imageView13.layout(left,top,right,bottom);
                        imageView13.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
//                        animatorSet.cancel();
                        break;
                }
                lastX = x;
                lastY = y;
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                velocityTracker = VelocityTracker.obtain();
//                float startX = event.getX();
//                float startY = event.getY();
//                LogUtils.e("起始坐标：startX="+startX+"；startY="+startY);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                velocityTracker.addMovement(event);
//                velocityTracker.computeCurrentVelocity(1000);
//                float xVelocity = velocityTracker.getXVelocity();
//                float yVelocity = velocityTracker.getYVelocity();
//                LogUtils.e("滑动速度：xVelocity="+xVelocity+"；yVelocity="+yVelocity);
//                break;
//            case MotionEvent.ACTION_UP:
//                float endX = event.getX();
//                float endY = event.getY();
//                LogUtils.e("结束坐标：endX="+endX+"；endY="+endY);
//                break;
//        }

        return gestureDetector.onTouchEvent(event);
    }

    @OnClick({R.id.button5, R.id.button6, R.id.imageView13})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button5:
//                imageView13.scrollTo(10,10);
//                tv.scrollTo(-10, -10);
                imageView13.startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate_xy));
                break;
            case R.id.button6:
//                imageView13.scrollBy(10,10);
//                tv.scrollBy(-10, -10);
//                ObjectAnimator.ofFloat(imageView13,"translationX",0,100).setDuration(1000).start();
                count +=10;
                AnimatorSet animatorSet=new AnimatorSet();
//                animatorSet.playTogether(ObjectAnimator.ofFloat(imageView13,"translationX",count,count).setDuration(1000),
//                        ObjectAnimator.ofFloat(imageView13,"translationY",count,count).setDuration(1000));
                animatorSet.playTogether(ObjectAnimator.ofFloat(imageView13,"translationX",count,count).setDuration(1000),
                        ObjectAnimator.ofFloat(imageView13,"translationY",count,count).setDuration(1000));
                animatorSet.start();
                break;
            case R.id.imageView13:
//                ToastUtils.showLong("点了图片");
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView13.getLayoutParams();
////                layoutParams.height=150;
////                layoutParams.weight=150;
//                layoutParams.leftMargin=200;
//                layoutParams.topMargin=200;
//                imageView13.setLayoutParams(layoutParams);
                scroller.startScroll(0,0,100,100,1000);

                break;
        }
    }
}
