package com.dyy.newtest.ui.activity.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;

import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncludeActivity extends AppCompatActivity {

    @BindView(R.id.show_vs)
    Button showVs;
    @BindView(R.id.hide_vs)
    Button hideVs;
    @BindView(R.id.vs)
    ViewStub vs;
    @BindView(R.id.show_error)
    Button showError;
    @BindView(R.id.iv_extra)
    ImageView ivExtra;
    private ImageView ivInflated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.show_vs, R.id.hide_vs, R.id.show_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_error:
                ivExtra.setVisibility(View.VISIBLE);
                break;
            case R.id.show_vs:
//                vs.inflate();
                vs.setVisibility(View.VISIBLE);
                ivInflated = (ImageView) findViewById(R.id.iv_splash);
                ivInflated.setImageResource(R.drawable.splash_ship);
                break;
            case R.id.hide_vs:
                vs.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
