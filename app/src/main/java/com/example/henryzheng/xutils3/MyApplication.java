package com.example.henryzheng.xutils3;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * Created by henryzheng on 2016/9/27.
 */
public class MyApplication extends Application {
    public static Context _context;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
    }
}
