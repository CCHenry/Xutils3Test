package com.example.henryzheng.xutils3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.henryzheng.xutils3.IdentityView.MyScroll;
import com.example.henryzheng.xutils3.IdentityView.MyViewPage;
import com.example.henryzheng.xutils3.IdentityView.SwitchButtonFragment;
import com.example.henryzheng.xutils3.ImageSortType.fragment.ImageSortFragment;
import com.example.henryzheng.xutils3.common.CCLog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main_page)
public class MainPageActivity extends BaseActivity {
    private List<BaseFragment> _fragments;
    @ViewInject(R.id.mainViewPager)
    private MyViewPage mainViewPager;
    @ViewInject(R.id.rl)
    private RelativeLayout rl;
    int viewSwitch=-1;
    @ViewInject(R.id.switch_fg)
    private SwitchButtonFragment switchButtonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
        MainPageAdapt mainPageAdapt=new MainPageAdapt(getSupportFragmentManager(), _fragments);
        mainViewPager.setAdapter(mainPageAdapt);
//        mainViewPager.setNoScroll(true);
        mainViewPager.setCurrentItem(0);
//        RelativeLayout relativeLayout=new RelativeLayout(this);
//        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        Button btn=new Button(this);
//        btn.setBackgroundResource(R.drawable.set);
//        btn.setLayoutParams(new RelativeLayout.LayoutParams(20,20));
//        btn.setGravity(View.TEXT_ALIGNMENT_CENTER);
////        relativeLayout.addView(btn);
//        rl.addView(btn);
        switchButtonFragment.setOnSwitchClickListener(new SwitchButtonFragment.OnSwitchClickListner() {
            int change=-1;
            @Override
            public void onClick() {
                change=-change;
                if (change==-1)
                    mainViewPager.setCurrentItem(0);
                else
                    mainViewPager.setCurrentItem(1);
                CCLog.print("change"+change);
            }
        });
//        setViewPagerScrollSpeed(mainViewPager,1300);
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
    private void setViewPagerScrollSpeed(ViewPager viewPager, int i){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            MyScroll scroller = new MyScroll( viewPager.getContext( ) );
            scroller.setmDuration(i);
            mScroller.set( viewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }
}
