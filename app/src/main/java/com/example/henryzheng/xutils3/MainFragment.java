package com.example.henryzheng.xutils3;


import android.content.Intent;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.henryzheng.xutils3.ImageShowRecycle.RecyclerImageFrament;
import com.example.henryzheng.xutils3.ImageSortType.fragment.ImageSortFragment;
import com.example.henryzheng.xutils3.MyHelp.MyHelp;
import com.example.henryzheng.xutils3.common.CCLog;
import com.example.henryzheng.xutils3.model.ImageModel;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

@ContentView(R.layout.fragment_main)
public class MainFragment extends BaseFragment {
    @ViewInject(R.id.mainPage)
    private ViewPager mainPager;
    @ViewInject(R.id.rl0)
    private RelativeLayout rl0;
    private List<BaseFragment> _fragments;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment();
        mainPager.setAdapter(new MainPageAdapt(getActivity().getSupportFragmentManager(), _fragments));

    }

    @Override
    public void onResume() {
        super.onResume();
        MainFragment mainFragment = (MainFragment) getActivity().getSupportFragmentManager().findFragmentByTag("mainFragment");
        NavigationFragment navigationFragment= (NavigationFragment) mainFragment.getChildFragmentManager().findFragmentById(R.id.fm);
        navigationFragment.setMainPage(mainPager);

    }

    private void initFragment() {
        _fragments = new ArrayList<>();
//        _fragments.add(new TestFragment());
        _fragments.add(new ImageSortFragment());
        _fragments.add(new RecyclerImageFrament());
        _fragments.add(new ImageSortFragment());
    }

    private class MainPageAdapt extends FragmentPagerAdapter {
        private List<BaseFragment> _fragments;

        public MainPageAdapt(FragmentManager fm, List<BaseFragment> fragment) {
            super(fm);
            _fragments = fragment;
        }

        @Override
        public Fragment getItem(int position) {
            return _fragments.get(position);
        }

        @Override
        public int getCount() {
            return _fragments.size();
        }
    }
    @Event(value = R.id.rl0,type = View.OnClickListener.class)
    private void OnClick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, JumpContans.tag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == JumpContans.tag) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
//                CCLog.print(/uri.toString());
                final BmobFile bmobFile=new BmobFile( new File(MyHelp.getRealFilePath(getActivity(),uri)));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
//                        CCLog.print(e.getMessage());
                        CCLog.print(bmobFile.getFileUrl());
                        ImageModel imageModel=new ImageModel();
                        imageModel.setUsername(getBmobUser().getUsername());
                        imageModel.setUrl(bmobFile.getFileUrl());
                        imageModel.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null)
                            CCLog.print("cause:"+e.getCause()+",errorcode"+e.getErrorCode());
                                else
                                    CCLog.print("submit Success:"+s);
                            }}
                        );
                    };
                });
            }

        }
    }}
