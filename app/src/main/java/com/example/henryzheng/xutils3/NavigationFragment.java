package com.example.henryzheng.xutils3;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.henryzheng.xutils3.common.CCLog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_navigation)
public class NavigationFragment extends BaseFragment {
    @ViewInject(R.id.rl)
    RelativeLayout rl;
    @ViewInject(R.id.lin)
    LinearLayout lin;
    int titleWidth = 0;
    float instanceX = 0;
    private ViewPager viewPager;
    @ViewInject(R.id.tv0)
    TextView tv0;
    @ViewInject(R.id.tv1)
    TextView tv1;
    @ViewInject(R.id.tv2)
    TextView tv2;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        rl.measure(width, height);
        height = rl.getMeasuredHeight();
        width = rl.getMeasuredWidth();
        titleWidth = width / 3;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin.getLayoutParams();
        layoutParams.width = titleWidth;
        lin.setLayoutParams(layoutParams);
        final int[] location = new int[2];
        rl.getLocationOnScreen(location);
        instanceX = location[0];
        CCLog.print("titleWidth:" + titleWidth + " instanceX:" + instanceX);
    }

    public void setMainPage(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int position = 0;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin.getLayoutParams();

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        layoutParams.leftMargin = (int) (titleWidth * positionOffset);
                        lin.requestLayout();
                        break;
                    case 1:
                        layoutParams.leftMargin =titleWidth+ (int) (titleWidth * positionOffset);
                        lin.requestLayout();

                        break;
                    case 2:
                        layoutParams.leftMargin =titleWidth*2+ (int) (titleWidth * positionOffset);
                        lin.requestLayout();

                        break;
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
