package com.example.henryzheng.xutils3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Display;

import org.xutils.x;

import cn.bmob.v3.BmobUser;


/**
 * Created by henryzheng on 2016/9/27.
 */
public class BaseActivity extends FragmentActivity implements IHandlerListener {
    private Display mDisplay;
    private static BmobUser bmobUser;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            OnHandlerListener(msg);
            bmobUser=MyApplication.mbmobUser;
        }
    };

    public static  BmobUser getBmobUser() {
        return bmobUser;
    }

    public static void setBmobUser(BmobUser bmobUser) {
        BaseActivity.bmobUser = bmobUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mDisplay = getWindowManager().getDefaultDisplay();
    }

    public int getWidth() {
        return mDisplay.getWidth();
    }

    public int getHight() {
        return mDisplay.getHeight();
    }

    @Override
    public void OnHandlerListener(Message object) {

    }
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler mHandler) {
        this.handler = mHandler;
    }

}
