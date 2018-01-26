package com.dyy.newtest.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DY on 2018/1/15.
 */

public class CityFragment extends Fragment{

    private RecyclerView rv;
    private HashMap<String,ArrayList<String>> citys;
    private ArrayList<String> js;
    private ArrayList<String> zhj;
    private ArrayList<String> shh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list_city, container, false);
        rv = (RecyclerView) view.findViewById(R.id.list);
        LogUtils.e("onCreateView");
        intiData();
        return view;
    }


    private void intiData() {
        citys=new HashMap<>();
        js=new ArrayList<>();
        zhj=new ArrayList<>();
        shh=new ArrayList<>();
        js.add("南京");
        js.add("苏州");
        js.add("无锡");
        js.add("常州");
        js.add("扬州");
        zhj.add("杭州");
        zhj.add("温州");
        zhj.add("嘉兴");
        zhj.add("金华");
        shh.add("陆家嘴");
        shh.add("浦东");
        shh.add("闵行");
        citys.put("江苏",js);
        citys.put("浙江",zhj);
        citys.put("上海",shh);
        LogUtils.e("intiData");
    }

    public void showCity(String key){
        ArrayList<String> lists = citys.get(key);
        rv.setAdapter(new CityAdapter(lists));
    }
}
