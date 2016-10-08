package com.example.henryzheng.xutils3.ImageSortType.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.henryzheng.xutils3.BaseFragment;
import com.example.henryzheng.xutils3.ImageSortType.adapt.ImageSortTypeAdapt;
import com.example.henryzheng.xutils3.ImageTypeActivity;
import com.example.henryzheng.xutils3.Interface.MyItemClickListener;
import com.example.henryzheng.xutils3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片类型选择Fragment
 */
@ContentView(R.layout.fragment_select_image_type)
public class ImageSortFragment extends BaseFragment {
    @ViewInject(R.id.recycleView)
    private RecyclerView _recyclerView;
    private ImageSortTypeAdapt _adapt;
    private List<Map<String, String>> _list;
    private String[] titles = new String[]{
            "风景",
            "美食",
            "动物",
            "动漫",
            "小清新",
            "科幻",
            "唯美",
            "美女",
    };
    private String[] urls = new String[]{
            "http://bbs.crsky.com/1236983883/Mon_1209/25_187069_54464ce0c7d1c2d.jpg",
            "http://imgstore.cdn.sogou.com/app/a/100540002/786019.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1469756649,1649042265&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3527562083,3958196011&fm=21&gp=0.jpg",
            "http://t-1.tuzhan.com/86ace0afc48f/c-2/l/2013/08/26/01/a28777d739b74b128f644e5eb136df88.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2194727581,2431101215&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3234292358,3812132590&fm=21&gp=0.jpg",
            "http://c.hiphotos.baidu.com/image/h%3D200/sign=4410fdbdab773912db268261c8198675/730e0cf3d7ca7bcbb8557c2fbc096b63f624a880.jpg",
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _adapt = new ImageSortTypeAdapt(getActivity(),2);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        _recyclerView.setLayoutManager(manager);
        _recyclerView.setAdapter(_adapt);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置增加或删除条目的动画
        _adapt.loadImgList(getImageInfoList());
        _adapt.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent=new Intent(getActivity(), ImageTypeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 返回图片的url和title列表
     * @return
     */
    private List<Map<String, String>> getImageInfoList() {
        _list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("url", urls[i]);
            map.put("title", titles[i]);
            _list.add(map);
        }
        return _list;
    }


}
