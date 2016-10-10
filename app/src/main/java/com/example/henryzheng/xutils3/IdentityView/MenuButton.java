package com.example.henryzheng.xutils3.IdentityView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.R.id;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuButton extends View implements OnClickListener {
    float delta1 = 1f;
    float delta2 = -1f;
    float x = 0;
    float y = 0;
    float orightX;
    float orightY;
    float radius1 = 20;
    float radius2 = 90;

    Boolean isPositive = true;
    Paint mPaint;

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(40);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        orightX = getWidth() * 3 / 4;
        orightY = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        delta1++;
        /** 圆周缩放 **/
        if (delta1 >= 100) {
            canvas.drawCircle(getWidth() / 4, getHeight() / 2, 200 - delta1, mPaint);
            if (delta1 == 200) {
                delta1 = 0;
            }
        } else {
            canvas.drawCircle(getWidth() / 4, getHeight() / 2, delta1, mPaint);
        }
        /** 圆周运动 **/
        if (isPositive) {
            x = x + 0.01f;
            if (x < 1) {
                y = -(float) Math.sqrt(1 - Math.pow(x, 2));
                canvas.drawCircle(x * radius2 + orightX, y * radius2 + orightY, radius1, mPaint);
            } else {

                canvas.drawCircle(x * radius2 + orightX, y * radius2 + orightY, radius1, mPaint);
                x = 1;
                isPositive = false;
            }
        } else {
            x = x - 0.01f;
            if (x > -1) {
                y = (float) Math.sqrt(1 - Math.pow(x, 2));
                canvas.drawCircle(x * radius2 + orightX, y * radius2 + orightY, radius1, mPaint);
            } else {
                canvas.drawCircle(x * radius2 + orightX, y * radius2 + orightY, radius1, mPaint);
                x = -1;
                isPositive = true;
            }
        }

        invalidate();
    }

    @Override
    public void onClick(View v) {
        postInvalidate();
        // onDraw(mCanvas);
    }

}
