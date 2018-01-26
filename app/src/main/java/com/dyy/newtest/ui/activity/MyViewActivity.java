package com.dyy.newtest.ui.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dyy.newtest.R;
import com.dyy.newtest.view.MyCustomView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyViewActivity extends AppCompatActivity {


    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.tv_child)
    MyCustomView tvChild;
//    @BindView(R.id.ll_parent)
//    MyLinearLayout llParent;
    @BindView(R.id.tv_show)
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_view);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @OnClick({R.id.tv_child, R.id.tv_show, R.id.btn_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_child:
                Toast.makeText(MyViewActivity.this,"myView被点击了",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_show:
                break;
            case R.id.btn_show:
//                tvChild.setAnimation(AnimationUtils.loadAnimation(MyViewActivity.this,R.anim.translate_x));
                ObjectAnimator.ofFloat(tvChild,"translationX",0,300).setDuration(1000).start();
                float toParentWhith = tvChild.getX();
                float toParentHeight = tvChild.getY();
                float toParentLeft = tvChild.getLeft();
                float toParentRight = tvChild.getRight();
                float toParentTop = tvChild.getTop();
                float toParentBottom = tvChild.getBottom();
                tvShow.setText("getX(200px):" + toParentWhith + ";getY(150px):" + toParentHeight + "\n" + "getLeft(150px):" + toParentLeft + ";getRight(250px):" + toParentRight + "\n" + "getTop(125px):" + toParentTop + ";getBottom(175px)" + toParentBottom);
                break;
        }
    }
}
