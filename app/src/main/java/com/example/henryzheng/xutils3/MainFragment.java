package com.example.henryzheng.xutils3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;

import com.example.henryzheng.xutils3.ImageShowRecycle.RecyclerImageFrament;
import com.example.henryzheng.xutils3.ImageSortType.fragment.ImageSortFragment;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_main)
public class MainFragment extends BaseFragment {
    @ViewInject(R.id.mainViewPager)
    private ViewPager mainViewPager;
    @ViewInject(R.id.fm)
    private NavigationFragment navigationFragment;

    private List<BaseFragment> _fragments;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Display mDisplay = getActivity().getWindowManager().getDefaultDisplay();
        initFragment();
        mainViewPager.setAdapter(new MainPageAdapt(getActivity().getSupportFragmentManager(), _fragments));

    }

    @Override
    public void onResume() {
        super.onResume();
        MainFragment mainFragment = (MainFragment) getActivity().getSupportFragmentManager().findFragmentByTag("mainFragment");
        NavigationFragment navigationFragment= (NavigationFragment) mainFragment.getChildFragmentManager().findFragmentById(R.id.fm);
        navigationFragment.setMainPage(mainViewPager);
    }

    private void initFragment() {
        _fragments = new ArrayList<>();
        _fragments.add(new RecyclerImageFrament());
        _fragments.add(new ImageSortFragment());
        _fragments.add(new ImageSortFragment());
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
