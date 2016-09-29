package com.example.henryzheng.xutils3.MyHelp;

import android.os.Environment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by henryzheng on 2016/9/28.
 */
public class MyHelp {
    public static String getCacheDir() {
        if (isSDCARDMounted()) {
            File file = new File(Environment.getExternalStorageDirectory(), "XutilsImageApp");
            if (!file.exists())
                file.mkdirs();
            return Environment.getExternalStorageDirectory() + "/XutilsImageApp";
        } else
            return null;
    }

    private static boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<String>();

        String regEx_img = "http:(.*?).jpg"; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(0);
            if (src.length() < 100) {
                pics.add(src.replace("\\", ""));
                //pics.add("http://f.hiphotos.baidu.com/zhidao/pic/item/2fdda3cc7cd98d104cc21595203fb80e7bec907b.jpg");
            }
        }
        List<String> temps = new ArrayList<String>();
        return pics;
    }

    public static List<String> getImgSrcList2(String htmlStr) {
        String IMGURL_REG = "objURL\":\"(http://).*?((.jpg)|(.jpeg)|(.png)|(.JPEG))";

        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(htmlStr);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group().replace("objURL\":\"", ""));
        }
        return listImgUrl;
    }

//    public void getImageFromBaidu(String searchText) {
//        String imageSite = "http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1475142861255_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=" + java.net.URLEncoder.encode(searchText);
//
//        x.http().get(new RequestParams(imageSite), new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                recycleAdapter.loadImgList(MyHelp.getImgSrcList(result));
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//        String IMGURL_REG = "objURL\":\"(http://).*?((.jpg)|(.jpeg)|(.png)|(.JPEG))";
//
//        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(htmlStr);
//        List<String> listImgUrl = new ArrayList<String>();
//        while (matcher.find()) {
//            listImgUrl.add(matcher.group().replace("objURL\":\"", ""));
//        }
//        return listImgUrl;
//    }
}
