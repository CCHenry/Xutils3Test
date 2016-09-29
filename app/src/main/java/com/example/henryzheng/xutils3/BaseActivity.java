package com.example.henryzheng.xutils3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;

import org.xutils.x;


/**
 * Created by henryzheng on 2016/9/27.
 */
public class BaseActivity extends Activity implements IHandlerListener {
    private Display mDisplay;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            OnHandlerListener(msg);
        }
    };

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
    public void OnHandlerListener(Object object) {

    }
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler mHandler) {
        this.handler = mHandler;
    }

}
