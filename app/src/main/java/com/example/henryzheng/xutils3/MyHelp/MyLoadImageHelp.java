package com.example.henryzheng.xutils3.MyHelp;

import com.example.henryzheng.xutils3.Interface.ImageLoadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by henryzheng on 2016/9/29.
 */
public class MyLoadImageHelp {
    ImageLoadListener mImageLoadListener;
    public static List<String> getUrlFromBaidu(String htmlStr) {
        String IMGURL_REG = "objURL\":\"(http://).*?((.jpg)|(.jpeg)|(.png)|(.JPEG))";
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(htmlStr);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group().replace("objURL\":\"", ""));
        }
        return listImgUrl;
    }
    public static List<String> getUrlFromNomal(String htmlStr) {
        List<String> pics = new ArrayList<String>();
        String regEx_img = "http:(.*?).jpg"; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(0);
            if (src.length() < 100) {
                pics.add(src.replace("\\", ""));
            }
        }
        List<String> temps = new ArrayList<String>();
        return pics;
    }
    public void startLoadUrl(ImageLoadListener listener){
        mImageLoadListener=listener;
    }
}
