package com.example.henryzheng.xutils3.IdentityView;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by henryzheng on 2016/10/10.
 */
public class MyScroll extends Scroller {
    private int mDuration = 0;

    public MyScroll(Context context) {
        super(context);
    }

    public MyScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public MyScroll(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }
}
