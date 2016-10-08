package com.example.henryzheng.xutils3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.example.henryzheng.xutils3.ImageSortType.fragment.ImageSortFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main_page)
public class MainPageActivity extends BaseActivity {
    private List<BaseFragment> _fragments;
    @ViewInject(R.id.mainViewPager)
    private ViewPager mainViewPager;
    int viewSwitch=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
        mainViewPager.setAdapter(new MainPageAdapt(getSupportFragmentManager(), _fragments));

    }

    private void initFragment() {
        _fragments = new ArrayList<>();
        _fragments.add(new ImageSortFragment());
        _fragments.add(new MainFragment());
        BaseFragment fragment0=new ImageSortFragment();
        BaseFragment fragment1=new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(fragment1,"mainFragment");
        ft.commit();
    }

    private class MainPageAdapt extends FragmentPagerAdapter {
        private List<BaseFragment> _fragments;
        private boolean isCanScroll = true;
        public MainPageAdapt(FragmentManager fm, List<BaseFragment> fragment) {
            super(fm);
            _fragments = fragment;
        }

        @Override
        public Fragment getItem(int position) {
            return _fragments.get(position);
        }

        @Override
        public int getCount() {
            return _fragments.size();
        }
        public void setScanScroll(boolean isCanScroll) {
            this.isCanScroll = isCanScroll;
        }


    }
}
