package com.example.henryzheng.xutils3.ImageShowRecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.henryzheng.xutils3.BaseFragment;
import com.example.henryzheng.xutils3.BigImageShowActivity;
import com.example.henryzheng.xutils3.Interface.MyItemClickListener;
import com.example.henryzheng.xutils3.JumpContans;
import com.example.henryzheng.xutils3.MyHelp.MyHelp;
import com.example.henryzheng.xutils3.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by henryzheng on 2016/9/27.
 */
@ContentView(R.layout.layout_image_recycle)
public class RecyclerImageFrament extends BaseFragment implements MyItemClickListener{
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
    private List<String> imageSiteList = new ArrayList<>();
    private String searchUrl = "http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&fm=detail&lm=-1&st=-1&sf=2&fmq=1470223098862_R_D&fm=detail&pv=&ic=0&nc=1&z=&se=&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleAdapter = new RecycleImageAdapt(getActivity());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);//设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);// 设置为垂直布局，这也是默认的
        recyclerView.setAdapter(recycleAdapter); // 设置Adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置增加或删除条目的动画
        imageSiteList = arrToList(imgSites);
//        loadPicture(imageSiteList);
        et.setText("风景");
        recycleAdapter.setOnItemClickListener(this);
        recyclerView.addItemDecoration(new RecycleItemDecoration(15));
        List<String> searchUrls = new ArrayList<>();
//        searchUrls.add("http://image.so.com/i?q=%E5%8A%A8%E7%89%A9&src=srp");
        searchUrls.add("http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1475142861255_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E5%A3%81%E7%BA%B8");

//        recycleAdapter.clear();
        loadPicture2(searchUrls);
    }

    private List<String> arrToList(String[] imgSites) {
        List<String> lists = new ArrayList<>();
        for (String imageSite : imgSites) {
            lists.add(imageSite);
        }
        return lists;
    }

    @Event(value = R.id.btn, type = View.OnClickListener.class)
    private void search(View view) {
        String text = et.getText().toString();
//        recycleAdapter.loadImgList2(getImgSite + text);
        List<String> searchUrls = new ArrayList<>();
//        searchUrls.add(searchUrl+text);
            recycleAdapter.clear();
//            searchUrls.add("http://image.so.com/i?q="+java.net.URLEncoder.encode(text)+"&src=srp");
        searchUrls.add("http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1475142861255_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word="+java.net.URLEncoder.encode(text));

//        recycleAdapter.clear();
        loadPicture2(searchUrls);
    }

    private void loadPicture(List<String> sites) {
        for (String imageSite : sites)
            x.http().get(new RequestParams(imageSite), new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    recycleAdapter.loadImgList(MyHelp.getImgSrcList(result));
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
    }

    /**
     * 百度爬虫
     * @param sites
     */
    private void loadPicture2(List<String> sites) {
        for (String imageSite : sites)
            x.http().get(new RequestParams(imageSite), new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    recycleAdapter.loadImgList(MyHelp.getImgSrcList2(result));
                    recycleAdapter.notifyDataSetChanged();

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
    }

    @Override
    public void onItemClick(View view, int postion) {
        Log.i("cctag", "postion:" + postion);
        Intent intent=new Intent(getActivity(), BigImageShowActivity.class);
        intent.putExtra(JumpContans.url,recycleAdapter.getUrls().get(postion));
        startActivity(intent);
    }
}
