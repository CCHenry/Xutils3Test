package com.example.henryzheng.xutils3.ImageShowList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.henryzheng.xutils3.BaseFragment;
import com.example.henryzheng.xutils3.BigImageShowActivity;
import com.example.henryzheng.xutils3.JumpContans;
import com.example.henryzheng.xutils3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by henryzheng on 2016/9/27.
 */
@ContentView(R.layout.layout_image_list)
public class ListImageFragment extends BaseFragment {
    @ViewInject(R.id.lv)
    private ListView lv;

    ListImageAdapt listImageAdapt;
    private String[] imgSites = {
            "http://image.baidu.com/",
            "http://www.22mm.cc/",
            "http://www.moko.cc/",
            "http://eladies.sina.com.cn/photo/",
            "http://www.youzi4.com/"
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listImageAdapt = new ListImageAdapt(getActivity());
        lv.setAdapter(listImageAdapt);
        for (String imageSite : imgSites)
            listImageAdapt.loadImgList(imageSite);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BigImageShowActivity.class);
                intent.putExtra(JumpContans.url, (String) listImageAdapt.getItem(position));
                startActivity(intent);
            }
        });
    }

    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add("http://" + src + ".jpg");
                //pics.add("http://f.hiphotos.baidu.com/zhidao/pic/item/2fdda3cc7cd98d104cc21595203fb80e7bec907b.jpg");
            }
        }
        return pics;
    }
}
