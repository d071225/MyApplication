package com.dyy.newtest.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.dyy.newtest.R;
import com.dyy.newtest.adapter.ConstellationAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupwindowActivity extends AppCompatActivity {

    @BindView(R.id.btn_pop)
    Button btnPop;
    private Context context;
    private String[] states={"承运中","已完成","已撤销"};
    private String[] dates={"今天","昨天","前天","一周前"};
    private String[] ways={"车辆","船舶"};
    private int state_position=-1;
    private int date_position=-1;
    private int way_position=-1;
    private ConstellationAdapter date_adapter;
    private ConstellationAdapter state_adapter;
    private ConstellationAdapter way_adapter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow);
        ButterKnife.bind(this);
        context=this;
    }

    @OnClick(R.id.btn_pop)
    public void onViewClicked() {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_item, null);
        GridView gv_date = (GridView) view.findViewById(R.id.gv_date);
        GridView gv_translate_state = (GridView) view.findViewById(R.id.gv_translate_state);
        GridView gv_translate_way = (GridView) view.findViewById(R.id.gv_translate_way);
        Button btn_commit = (Button) view.findViewById(R.id.btn_commit);
        date_adapter = new ConstellationAdapter(context, Arrays.asList(dates));
        state_adapter = new ConstellationAdapter(context, Arrays.asList(states));
        way_adapter = new ConstellationAdapter(context, Arrays.asList(ways));
        gv_date.setAdapter(date_adapter);
        gv_translate_state.setAdapter(state_adapter);
        gv_translate_way.setAdapter(way_adapter);
        gv_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                date_adapter.setCheckItem(position);
                if (date_adapter.getCheckedState()) {
                    date_position = position;
                }else {
                    date_position = -1;
                }
            }
        });
        gv_translate_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state_adapter.setCheckItem(position);
                if (state_adapter.getCheckedState()) {
                    state_position = position;
                }else {
                    state_position = -1;
                }
            }
        });
        gv_translate_way.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                way_adapter.setCheckItem(position);
                if (way_adapter.getCheckedState()) {
                    way_position = position;
                }else {
                    way_position = -1;
                }
            }
        });
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(state_position==-1?null:states[state_position]);
                System.out.println(date_position==-1?null:dates[date_position]);
                System.out.println(way_position==-1?null:ways[way_position]);
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundAlpha(0.5f);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }
}
