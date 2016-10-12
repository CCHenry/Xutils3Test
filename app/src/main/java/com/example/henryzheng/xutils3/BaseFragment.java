package com.example.henryzheng.xutils3;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import cn.bmob.v3.BmobUser;

/**
 * Created by henryzheng on 2016/9/27.
 */
public class BaseFragment extends Fragment implements IHandlerListener {
    private static BmobUser bmobUser;

    private boolean injected = false;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            OnHandlerListener(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        bmobUser=MyApplication.mbmobUser;
        return x.view().inject(this, inflater, container);
    }

    public static  BmobUser getBmobUser() {
        return bmobUser;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    @Override
    public void OnHandlerListener(Message msg) {

    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler mHandler) {
        this.handler = mHandler;
    }
}
