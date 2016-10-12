package com.example.henryzheng.xutils3.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by henryzheng on 2016/10/12.
 */
public class ImageModel extends BmobObject{
    private String url;
    private String username;
    private String favorite;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
