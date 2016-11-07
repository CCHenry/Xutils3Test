package com.example.henryzheng.xutils3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.henryzheng.xutils3.common.CCLog;
import com.example.henryzheng.xutils3.model.MyUser;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @ViewInject(R.id.email)
    private AutoCompleteTextView emailET;
    @ViewInject(R.id.password)
    private EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = {R.id.btn0}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn0:
                final String mEmail = emailET.getText().toString();
                final String mPassword = passwordET.getText().toString();
                MyApplication.mbmobUser.setUsername(mEmail);
                MyApplication.mbmobUser.setPassword(mPassword);
                MyApplication.mbmobUser.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (myUser != null) {
                            getBmobUser().setUsername(mEmail);
                            getBmobUser().setPassword(mPassword);
                            getBmobUser().signUp(new SaveListener<BmobUser>() {

                                @Override
                                public void done(BmobUser bmobUser, BmobException e) {
                                    if (e == null) {
                                        CCLog.print("登录成功:");
                                        startActivity(new Intent(context, MainPageActivity.class));
                                        //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                                        //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                                    } else {
                                        CCLog.print(e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
                break;

            default:
                break;
        }
    }
}

