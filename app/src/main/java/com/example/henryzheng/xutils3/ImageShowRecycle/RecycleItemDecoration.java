package com.example.henryzheng.xutils3.ImageShowRecycle;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by henryzheng on 2016/9/29.
 */
public class RecycleItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public RecycleItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        outRect.left=space;
//        outRect.right=space;
//        outRect.bottom=space;
//        if(parent.getChildAdapterPosition(view)==0){
//            outRect.top=space;
//        }
//        if(parent.getChildAdapterPosition(view)==2){
//            outRect.top=space*2/3;
//        }
    }
}
