package com.dyy.newtest.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import java.util.ArrayList;
import java.util.List;


public class ProFragment extends Fragment {
    private List<String> pros;
    private FragmentActivity mActivity;
    private CityFragment cf;
    private int mIndex;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        cf = (CityFragment) mActivity.getSupportFragmentManager().findFragmentById(R.id.fg_right);
        LogUtils.e("cf--->"+cf);
        initData();
//        if (savedInstanceState!=null) {
//            LogUtils.e("savedInstanceState--->"+mIndex);
//            cf.showCity(pros.get(mIndex));
//        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(pros, getActivity());
            recyclerView.setAdapter(adapter);
            adapter.SetOnProClickListener(new MyItemRecyclerViewAdapter.OnProClickListener() {
                @Override
                public void onClick(int pos) {
                    cf.showCity(pros.get(pos));
                    mIndex=pos;
                }
            });
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("mIndex",mIndex);
        LogUtils.e("onSaveInstanceState--->"+mIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e("savedInstanceState--->"+mIndex);
        if (savedInstanceState!=null) {
            LogUtils.e("savedInstanceState--->"+mIndex);
            cf.showCity(pros.get(mIndex));
        }
    }

    private void initData() {
        pros=new ArrayList<>();
        pros.add("江苏");
        pros.add("上海");
        pros.add("浙江");
    }

}
