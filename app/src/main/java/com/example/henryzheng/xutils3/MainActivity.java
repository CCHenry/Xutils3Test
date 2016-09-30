package com.example.henryzheng.xutils3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.widget.Button;

import com.example.henryzheng.xutils3.ImageSelectType.adapt.ImageSelectTypeAdapt;
import com.example.henryzheng.xutils3.ImageSelectType.fragment.SelectImageTypeFragment;
import com.example.henryzheng.xutils3.ImageShowRecycle.RecyclerImageFrament;
import com.example.henryzheng.xutils3.common.CCLog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.btn)
    private Button btn;
    @ViewInject(R.id.mainViewPager)
    private ViewPager mainViewPager;
    @ViewInject(R.id.fm)
    private NavigationFragment navigationFragment;
    private List<BaseFragment> _fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display mDisplay = getWindowManager().getDefaultDisplay();
        initFragment();
        mainViewPager.setAdapter(new MainPageAdapt(getSupportFragmentManager(), _fragments));
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationFragment fragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.fm);
        fragment.setMainPage(mainViewPager);

    }


    private void initFragment() {
        _fragments = new ArrayList<>();
        _fragments.add(new SelectImageTypeFragment());
        _fragments.add(new RecyclerImageFrament());
        _fragments.add(new SelectImageTypeFragment());

    }

    private class MainPageAdapt extends FragmentPagerAdapter {
        private List<BaseFragment> _fragments;

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
    }

}
