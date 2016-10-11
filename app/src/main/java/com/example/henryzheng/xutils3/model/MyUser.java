package com.example.henryzheng.xutils3.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by henryzheng on 2016/10/11.
 */
public class MyUser extends BmobUser {
    private String name;
    private String[] submitPic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
