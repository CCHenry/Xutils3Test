package com.example.henryzheng.xutils3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import org.xutils.view.annotation.ContentView;

import cn.bmob.v3.BmobUser;

@ContentView(R.layout.activity_load)
public class LoadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.mbmobUser = BmobUser.getCurrentUser();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    if (MyApplication.mbmobUser == null) {
                        startActivity(new Intent(LoadActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(LoadActivity.this, MainPageActivity.class));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
