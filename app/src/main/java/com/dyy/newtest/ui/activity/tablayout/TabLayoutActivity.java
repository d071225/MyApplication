package com.dyy.newtest.ui.activity.tablayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutActivity extends AppCompatActivity {

    @BindView(R.id.tl)
    TabLayout tl;
    @BindView(R.id.vp)
    ViewPager vp;
    private String[] titles = {"关注", "推荐", "本地", "视频", "音乐", "体育", "房产", "科技"};
    private ContentAdapter contentAdapter;
    private List<BlankTestFragment> fragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
        contentAdapter = new ContentAdapter(getSupportFragmentManager());
        vp.setAdapter(contentAdapter);
//        tl.setupWithViewPager(vp);
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tl.newTab();
            View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
            TextView tv_tab_right = (TextView) view.findViewById(R.id.tv_tab_right);
            ImageView iv_tab_left = (ImageView) view.findViewById(R.id.iv_tab_left);
            iv_tab_left.setImageResource(R.mipmap.ic_launcher_round);
            tv_tab_right.setText(titles[i]);
            tab.setCustomView(view);
            tl.addTab(tab);
        }
        vp.setOffscreenPageLimit(8);
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
//                BlankTestFragment item = (BlankTestFragment) contentAdapter.getItem(position);
//                BlankTestFragment item = fragments.get(position);
//                LogUtils.e(item.toString()+";"+position);
//                item.initData();
            }
        });
        tl.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));
    }

    public class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BlankTestFragment blankTestFragment = BlankTestFragment.newInstance(position + 1);
            LogUtils.e(blankTestFragment.toString()+";getItem:"+position);
//            fragments.add(blankTestFragment);
            return blankTestFragment;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            LogUtils.e("ContentAdapter destroyItem position="+position);
//            fragments.remove(position);
        }
    }

    @SuppressLint("ValidFragment")
    public static class BlankTestFragment extends Fragment {
        public static final String COUNTKEY = "count";
//        @BindView(R.id.tv_content)
        TextView tvContent;
//        @BindView(R.id.iv)
        ImageView iv;
        private int count;
        private Bitmap bitmap;
        private boolean prepared=false;

        public static BlankTestFragment newInstance(int selected) {
            BlankTestFragment blankTestFragment = new BlankTestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(COUNTKEY, selected);
            blankTestFragment.setArguments(bundle);
            return blankTestFragment;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            boolean userVisibleHint = getUserVisibleHint();
            LogUtils.e("fragment "+count+"setUserVisibleHint  getUserVisibleHint:"+userVisibleHint);
            if (userVisibleHint){
                lazyload();
            }else {
                hintData();
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            LogUtils.e(count + "-----------onCreateView------------------");
            View view = inflater.inflate(R.layout.fragment_content, container, false);
//            ButterKnife.bind(this, view);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            iv = (ImageView) view.findViewById(R.id.iv);
            prepared =true;
            lazyload();
            return view;
        }
        public void lazyload(){
            if (prepared){
                initData();
            }
        }
        public void hintData(){
            if (bitmap!=null) {
                bitmap.recycle();
                bitmap = null;
                iv.setImageBitmap(null);
            }
        }
        public void initData() {
//            tvContent.setText("This is Fragment" + count);
//            iv.setImageResource(R.drawable.splash_ship_nodpi);
            tvContent.setText("This is Fragment" + count);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_ship);
            int byteCount = bitmap.getByteCount();
            LogUtils.e("加载图片内存大小:"+byteCount+";宽："+ bitmap.getWidth()+";高："+ bitmap.getHeight());
            iv.setImageBitmap(bitmap);
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            count = getArguments().getInt(COUNTKEY);
            LogUtils.e(count + "-----------onAttach------------------");
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            LogUtils.e(count + "-----------onCreate------------------");
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            LogUtils.e(count + "-----------onActivityCreated------------------");
        }

        @Override
        public void onStart() {
            super.onStart();
            LogUtils.e(count + "-----------onStart------------------");
        }

        @Override
        public void onResume() {
            super.onResume();
            LogUtils.e(count + "-----------onResume------------------");
        }

        @Override
        public void onStop() {
            super.onStop();
//            LogUtils.e(count + "-----------onStop------------------");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            LogUtils.e(count + "-----------onDestroy------------------");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
//            LogUtils.e(count + "-----------onDestroyView------------------");
        }

        @Override
        public void onDetach() {
            super.onDetach();
//            LogUtils.e(count + "-----------onDetach------------------");
        }

    }
}
