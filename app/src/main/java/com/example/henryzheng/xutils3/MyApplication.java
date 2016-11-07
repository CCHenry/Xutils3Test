package com.example.henryzheng.xutils3;

import android.app.Application;
import android.content.Context;

import com.tencent.tauth.Tencent;

import org.xutils.x;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by henryzheng on 2016/9/27.
 */
public class MyApplication extends Application {
    public static Context _context;
    public static Tencent mTencent;
    public static BmobUser mbmobUser;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        Bmob.initialize(this, "e17abe6223cb8a16d02b559e972724f4");
//        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        mTencent = Tencent.createInstance("1105732414", this.getApplicationContext());
        if (BmobUser.getCurrentUser() == null)
            mbmobUser = new BmobUser();
        else
            mbmobUser = BmobUser.getCurrentUser();
    }
}
