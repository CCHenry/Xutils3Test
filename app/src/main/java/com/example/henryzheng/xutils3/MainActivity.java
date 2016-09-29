package com.example.henryzheng.xutils3;

import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.widget.Button;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity{
    @ViewInject(R.id.btn)
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display mDisplay = getWindowManager().getDefaultDisplay();

    }


}
