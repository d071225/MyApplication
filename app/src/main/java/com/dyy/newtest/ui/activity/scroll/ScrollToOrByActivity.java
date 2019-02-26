package com.dyy.newtest.ui.activity.scroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScrollToOrByActivity extends AppCompatActivity {

    @BindView(R.id.scrollTo)
    Button scrollTo;
    @BindView(R.id.scrollBy)
    Button scrollBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_to_or_by);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.scrollTo, R.id.scrollBy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scrollTo:
                ((View)scrollTo.getParent()).scrollTo(0,100);
                break;
            case R.id.scrollBy:
                ((View)scrollBy.getParent()).scrollBy(0,100);
                break;
        }
    }
}
