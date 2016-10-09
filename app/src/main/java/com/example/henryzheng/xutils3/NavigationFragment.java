package com.example.henryzheng.xutils3;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.henryzheng.xutils3.common.CCLog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_navigation)
public class NavigationFragment extends BaseFragment {
    @ViewInject(R.id.lin5)
    LinearLayout lin;// 标签的layout
    @ViewInject(R.id.lin0)
    LinearLayout lin0;// title的layout
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
        lin0.measure(width, height);
        height = lin0.getMeasuredHeight();
        width = lin0.getMeasuredWidth();
        titleWidth = width / 3;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin.getLayoutParams();
        layoutParams.width = titleWidth;
        lin.setLayoutParams(layoutParams);
        final int[] location = new int[2];
        lin0.getLocationOnScreen(location);
        instanceX = location[0];
        CCLog.print("titleWidth:" + titleWidth + " instanceX:" + instanceX);

    }

    /**
     * 设置viewpager，监听viewpager，让标识移动
     *
     * @param viewPager
     */
    public void setMainPage(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int position = 0;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin.getLayoutParams();

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        layoutParams.leftMargin = (int) (titleWidth * positionOffset);
                        lin.requestLayout();
                        break;
                    case 1:
                        layoutParams.leftMargin = titleWidth + (int) (titleWidth * positionOffset);
                        lin.requestLayout();
                        break;
                    case 2:
                        layoutParams.leftMargin = titleWidth * 2 + (int) (titleWidth * positionOffset);
                        lin.requestLayout();
                        break;
                }
                lin.invalidate();
                CCLog.print("layoutParams.leftMargin:" + layoutParams.leftMargin);

//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin.getLayoutParams();
//                layoutParams.leftMargin = 30 + layoutParams.leftMargin;
//                lin.requestLayout();
//                CCLog.print("layoutParams.leftMargin:" + layoutParams.leftMargin);
//                CCLog.print("position:" + position);
//                CCLog.print("leftMargin:" + (int) (titleWidth * positionOffset));
//                CCLog.print("layoutParams.leftMargin:" + layoutParams.leftMargin);
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin.getLayoutParams();
//                layoutParams.leftMargin = 30 + layoutParams.leftMargin;
//                lin.requestLayout();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Event(value = R.id.btn)
    private void onclick(View view) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin.getLayoutParams();
        CCLog.print("onclick before layoutParams.leftMargin:" + layoutParams.leftMargin);

        layoutParams.leftMargin = 30 + layoutParams.leftMargin;
        lin.requestLayout();
        CCLog.print("onclick after layoutParams.leftMargin:" + layoutParams.leftMargin);
    }
}
