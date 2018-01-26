package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LayoutInflaterActivity extends AppCompatActivity {

    @BindView(R.id.rcv)
    RecyclerView rcv;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.btn_add)
    Button btnAdd;
    private String[] titles = {"测试1", "测试2", "测试3", "测试4", "测试5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);
        ButterKnife.bind(this);

//        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv.setAdapter(new LayoutInflaterAdapter());
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_inflater_view, null, false);
        llParent.addView(view);
    }

    public class LayoutInflaterAdapter extends RecyclerView.Adapter<LayoutInflaterAdapter.LayoutInflaterVH> {

        @Override
        public LayoutInflaterVH onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = View.inflate(LayoutInflaterActivity.this, R.layout.layout_inflater_view, parent);
            View view = LayoutInflater.from(LayoutInflaterActivity.this).inflate(R.layout.layout_inflater_view, parent,false);
            return new LayoutInflaterVH(view);
        }

        @Override
        public void onBindViewHolder(LayoutInflaterVH holder, int position) {

        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        public class LayoutInflaterVH extends RecyclerView.ViewHolder {

            private final TextView tv_1;
            private final TextView tv_2;

            public LayoutInflaterVH(View itemView) {
                super(itemView);
                tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
                tv_2 = (TextView) itemView.findViewById(R.id.tv_2);
            }
        }
    }
}
