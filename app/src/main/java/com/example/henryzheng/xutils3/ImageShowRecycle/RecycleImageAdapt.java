package com.example.henryzheng.xutils3.ImageShowRecycle;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.henryzheng.xutils3.BaseActivity;
import com.example.henryzheng.xutils3.BigImageShowActivity;
import com.example.henryzheng.xutils3.ImageShowList.ListImageAdapt;
import com.example.henryzheng.xutils3.Interface.MyItemClickListener;
import com.example.henryzheng.xutils3.JumpContans;
import com.example.henryzheng.xutils3.R;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by henryzheng on 2016/9/27.
 */
public class RecycleImageAdapt extends RecyclerView.Adapter<RecycleImageAdapt.MyViewHolder> {
    private final ImageOptions _imageOptions;
    Context _context;
    List<String> urls;
    LayoutInflater _mLayoutInflater;
    MyItemClickListener myItemClickListener;

    public RecycleImageAdapt(Context context) {
        _context = context;
        _mLayoutInflater = LayoutInflater.from(context);
        urls = new ArrayList<>();
        _imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(200))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
//                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
//                .setCircular(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .setFadeIn(true)
                .build();
    }

    public void addSrc(List<String> datas) {
        this.urls.addAll(datas);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _mLayoutInflater.inflate(R.layout.layout_image_recycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, myItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        x.image().bind(holder.iv, urls.get(position), _imageOptions, new RecycleImageAdapt.CustomBitmapLoadCallBack(holder));
//        if (holder.iv.getWidth() > holder.iv.getHeight())
        int width=((BaseActivity) _context).getWidth() / 3;
//        int height=(int)(width*3/4+Math.random()*(width*7/4-width*3/4+1));
        holder.iv.setLayoutParams(new LinearLayout.LayoutParams(width, width));

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv;
        ProgressBar pb;
        MyItemClickListener _mItemClickListener;

        public MyViewHolder(final View view, MyItemClickListener _mItemClickListener) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            pb = (ProgressBar) view.findViewById(R.id.pb);
            view.setOnClickListener(this);
            this._mItemClickListener = _mItemClickListener;
        }

        @Override
        public void onClick(View v) {
            _mItemClickListener.onItemClick(v, getPosition());
        }
    }

    public class CustomBitmapLoadCallBack implements Callback.ProgressCallback<Drawable> {
        private final MyViewHolder holder;

        public CustomBitmapLoadCallBack(MyViewHolder holder) {
            this.holder = holder;

        }

        @Override
        public void onWaiting() {
            this.holder.pb.setProgress(0);

        }


        @Override
        public void onStarted() {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {
            this.holder.pb.setProgress((int) (current * 100 / total));

        }

        @Override
        public void onSuccess(Drawable result) {
            this.holder.pb.setProgress(100);

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
    }

    public void loadImgList(List<String> urls) {
        addSrc(urls);
        notifyDataSetChanged();//通知listview更新数据
    }


    public void setOnItemClickListener(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    public void clear() {
        urls.clear();
    }

    public List<String> getUrls() {
        return urls;
    }
}
