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
    List<String> _urls;
    LayoutInflater _mLayoutInflater;

    public RecycleImageAdapt(Context context) {
        _context = context;
        _mLayoutInflater = LayoutInflater.from(context);
        _urls = new ArrayList<>();
        _imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
//                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
//                .setCircular(true)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .setFadeIn(true)
                .build();
    }

    public void addSrc(List<String> datas) {
        this._urls.addAll(datas);
    }

    public void notifiy() {
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _mLayoutInflater.inflate(R.layout.layout_image_recycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        x.image().bind(holder.iv, _urls.get(position), _imageOptions, new RecycleImageAdapt.CustomBitmapLoadCallBack(holder));
//        if (holder.iv.getWidth() > holder.iv.getHeight())
        holder.iv.setLayoutParams(new LinearLayout.LayoutParams(((BaseActivity) _context).getWidth() / 4, ((BaseActivity) _context).getWidth() / 4));
//        else
//            holder.iv.setLayoutParams(new LinearLayout.LayoutParams(((BaseActivity) _context).getWidth() / 4, ((BaseActivity) _context).getWidth() / 4));

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, BigImageShowActivity.class);
                intent.putExtra(JumpContans.url, (String) _urls.get(position));
                _context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _urls.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        ProgressBar pb;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            pb = (ProgressBar) view.findViewById(R.id.pb);
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

    public void loadImgList(String url) {
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                addSrc(getImgSrcList(result));
                notifyDataSetChanged();//通知listview更新数据
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

    public void loadImgList2(String url) {
        _urls.clear();
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                addSrc(getImgSrcList2(result));
                notifyDataSetChanged();//通知listview更新数据
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

    public static List<String> getImgSrcList2(String htmlStr) {
        String IMGURL_REG = "objURL\":\"(http://).*?((.jpg)|(.jpeg)|(.png)|(.JPEG))";

        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(htmlStr);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group().replace("objURL\":\"", ""));
        }
        return listImgUrl;
    }
}
