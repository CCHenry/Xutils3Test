package com.example.henryzheng.xutils3.ImageSortType.adapt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.henryzheng.xutils3.BaseActivity;
import com.example.henryzheng.xutils3.Interface.MyItemClickListener;
import com.example.henryzheng.xutils3.R;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 选择图片类型
 * Created by henryzheng on 2016/9/27.
 */
public class ImageSortTypeAdapt extends RecyclerView.Adapter<ImageSortTypeAdapt.MyViewHolder> {
    private final ImageOptions _imageOptions;
    Context _context;

    List<Map<String,String>> dataList;
    LayoutInflater _mLayoutInflater;
    MyItemClickListener myItemClickListener;
    int spanCount=0;
    public ImageSortTypeAdapt(Context context, int spanCount) {
        _context = context;
        _mLayoutInflater = LayoutInflater.from(context);
        dataList=new ArrayList<>();
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
        this.spanCount=spanCount;
    }

    public void addSrc(List<Map<String,String>> dataList) {
        this.dataList.addAll(dataList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _mLayoutInflater.inflate(R.layout.layout_image_recycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, myItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        x.image().bind(holder.iv, dataList.get(position).get("url"), _imageOptions, new ImageSortTypeAdapt.CustomBitmapLoadCallBack(holder));
        holder.tv.setText( dataList.get(position).get("title"));
//        if (holder.iv.getWidth() > holder.iv.getHeight())
        int width = ((BaseActivity) _context).getWidth() /spanCount;
//        int height=(int)(width*3/4+Math.random()*(width*7/4-width*3/4+1));
        holder.iv.setLayoutParams(new RelativeLayout.LayoutParams(width, width));
        ViewTreeObserver vto = holder.iv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                holder.iv.getHeight();
                holder.iv.setLayoutParams(new RelativeLayout.LayoutParams(holder.iv.getWidth(), holder.iv.getWidth()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv;
        ProgressBar pb;
        TextView tv;
        MyItemClickListener _mItemClickListener;

        public MyViewHolder(final View view, MyItemClickListener _mItemClickListener) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            pb = (ProgressBar) view.findViewById(R.id.pb);
            tv=(TextView )view.findViewById(R.id.tv);

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

    public void loadImgList(List<Map<String,String>> dataList) {

        addSrc(dataList);
        notifyDataSetChanged();//通知listview更新数据
    }


    public void setOnItemClickListener(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    public void clear() {
        dataList.clear();
    }

    public List<Map<String,String>> getUrls() {
        return dataList;
    }
}
