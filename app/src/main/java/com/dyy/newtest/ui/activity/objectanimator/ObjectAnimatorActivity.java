package com.dyy.newtest.ui.activity.objectanimator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.view.MyAnimationView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ObjectAnimatorActivity extends AppCompatActivity {

    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.btn_trans)
    Button btnTrans;
    @BindView(R.id.btn_rotate)
    Button btnRotate;
    @BindView(R.id.btn_alpha)
    Button btnAlpha;
    @BindView(R.id.btn_scale)
    Button btnScale;
    @BindView(R.id.myanimview)
    MyAnimationView myanimview;
    private int distanceX;
    private int startX1;
    private int lastY;
    private int lastX;
    private int startX;
    private int startY;
    private AnimatorSet animatorSet;
    private int posX;
    private int posY;
    private int distanceY;
    private int startY1;
    private int x;
    private int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        ButterKnife.bind(this);
        animatorSet = new AnimatorSet();
        button7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x += startX - lastX;
                        y += startY - lastY;
                        animatorSet.play(ObjectAnimator.ofFloat(button7, "translationX", posX, x))
                                .with(ObjectAnimator.ofFloat(button7, "translationY", posY, y));
                        animatorSet.start();
                        posX = x;
                        posY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                lastX = startX;
                lastY = startY;
                return true;
            }
        });
    }


    @OnClick({R.id.btn_trans, R.id.btn_rotate, R.id.btn_alpha, R.id.btn_scale})
    public void onClick(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        switch (view.getId()) {
            case R.id.btn_trans:
                int i = new Random().nextInt(100) + 1;
                int j = new Random().nextInt(100) + 1;
                LogUtils.e("随机数i:" + i + "随机数j:" + j);
                distanceX += i;
                distanceY += j;
                LogUtils.e("随机数x:" + distanceX + "随机数Y:" + distanceY);
                LogUtils.e("起始坐标x:" + startX1 + "起始坐标Y:" + startY1);
                LogUtils.e("宽:" + ScreenUtils.getScreenWidth() + "高:" + ScreenUtils.getScreenHeight());
                animatorSet.play(ObjectAnimator.ofFloat(button7, "translationX", startX1, distanceX)).
                        before(ObjectAnimator.ofFloat(button7, "translationY", startY1, distanceY));
                animatorSet.start();
                startX1 = distanceX;
                startY1 = distanceY;
                break;
            case R.id.btn_rotate:
                break;
            case R.id.btn_alpha:
                ObjectAnimator.ofFloat(myanimview,"radius",0,300,100).setDuration(1000).start();
                break;
            case R.id.btn_scale:

                ObjectAnimator.ofFloat(button7, "translationX", 0, 100).setDuration(1000).start();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ObjectAnimator.ofFloat(button7, "translationX", 100, 0).setDuration(2000).start();
//                animatorSet.play(ObjectAnimator.ofFloat(button7,"translationX",0,100).setDuration(1000)).
//                        before(ObjectAnimator.ofFloat(button7,"translationX",0,-100).setDuration(2000));
//                animatorSet.start();
//                ObjectAnimator.ofFloat(button7,"ScaleX",0,1).setDuration(1000).start();
                break;
        }
    }
}
