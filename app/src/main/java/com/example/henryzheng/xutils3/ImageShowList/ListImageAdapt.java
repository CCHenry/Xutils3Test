package com.example.henryzheng.xutils3.ImageShowList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
public class ListImageAdapt extends BaseAdapter {
    private final ImageOptions _imageOptions;
    Context _context;
    List<String> _urls;
    LayoutInflater _mLayoutInflater;

    public ListImageAdapt(Context context) {
        _context = context;
        _mLayoutInflater = LayoutInflater.from(context);
        _urls=new ArrayList<>();
        _imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(200))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setCircular(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();

    }
    public  void loadImgList(String url) {
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
    public void addSrc(List<String> imgSrcList) {
        this._urls.addAll(imgSrcList);
    }

    public void addSrc(String imgUrl) {
        this._urls.add(imgUrl);
    }

    @Override
    public int getCount() {
        return _urls.size();
    }

    @Override
    public Object getItem(int position) {
        return _urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItemHolder holder = null;
        if (convertView == null) {
            convertView = _mLayoutInflater.inflate(R.layout.layout_image_list_item, null);
            holder = new ImageItemHolder();
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ImageItemHolder) convertView.getTag();
        }
        holder.pb.setProgress(0);
        x.image().bind(holder.iv, _urls.get(position), _imageOptions, new CustomBitmapLoadCallBack(holder));
        return convertView;
    }

    private class ImageItemHolder {
        @ViewInject(R.id.iv)
        private ImageView iv;
        @ViewInject(R.id.pb)
        private ProgressBar pb;
    }

    public class CustomBitmapLoadCallBack implements Callback.ProgressCallback<Drawable> {
        private final ImageItemHolder holder;

        public CustomBitmapLoadCallBack(ImageItemHolder holder) {
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
