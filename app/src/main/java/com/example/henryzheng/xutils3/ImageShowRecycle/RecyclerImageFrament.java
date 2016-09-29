package com.example.henryzheng.xutils3.ImageShowRecycle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.henryzheng.xutils3.BaseFragment;
import com.example.henryzheng.xutils3.BigImageShowActivity;
import com.example.henryzheng.xutils3.ImageShowList.ListImageAdapt;
import com.example.henryzheng.xutils3.JumpContans;
import com.example.henryzheng.xutils3.MainActivity;
import com.example.henryzheng.xutils3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by henryzheng on 2016/9/27.
 */
@ContentView(R.layout.layout_image_recycle)
public class RecyclerImageFrament extends BaseFragment {
    @ViewInject(R.id.recycleView)
    private RecyclerView recyclerView;
    @ViewInject(R.id.btn)
    private Button btn;
    @ViewInject(R.id.et)
    private EditText et;
    private List<String> mDatas;
    private RecycleImageAdapt recycleAdapter;
    private String[] imgSites = {
            "http://image.baidu.com/",
            "http://www.22mm.cc/",
            "http://www.moko.cc/",
            "http://eladies.sina.com.cn/photo/",
            "http://www.youzi4.com/"
    };
    private String getImgSite="http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&fm=detail&lm=-1&st=-1&sf=2&fmq=1470223098862_R_D&fm=detail&pv=&ic=0&nc=1&z=&se=&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=";
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recycleAdapter= new RecycleImageAdapt(getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),4);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter( recycleAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        for (String imageSite:imgSites)
            recycleAdapter.loadImgList(imageSite);
        et.setText("风景");
    }
    @Event(value = R.id.btn,type = View.OnClickListener.class)
    private void search(View view){
        String text=et.getText().toString();
        recycleAdapter.loadImgList2(getImgSite+text);
    }

}
