package com.example.henryzheng.xutils3.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.henryzheng.xutils3.BaseFragment;
import com.example.henryzheng.xutils3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_test)
public class TestFragment extends BaseFragment {
@ViewInject(R.id.btn)
private Button btn;
    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Event(value = {R.id.btn})
    private void onClick(View view){
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin=layoutParams.leftMargin+40;
        btn.requestLayout();
//        btn.invalidate();
}}
