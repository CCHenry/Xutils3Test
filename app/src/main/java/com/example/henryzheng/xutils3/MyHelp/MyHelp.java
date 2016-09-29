package com.example.henryzheng.xutils3.MyHelp;

import android.os.Environment;

import java.io.File;

/**
 * Created by henryzheng on 2016/9/28.
 */
public class MyHelp {
    public static  String getCacheDir(){
        if (isSDCARDMounted()){
            File file=new File(Environment.getExternalStorageDirectory(),"XutilsImageApp");
            if (!file.exists())
                file.mkdirs();
            return Environment.getExternalStorageDirectory()+"/XutilsImageApp";
        }
        else
            return null;
    }
    private static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }
}
