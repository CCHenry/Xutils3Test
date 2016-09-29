package com.example.henryzheng.xutils3;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.henryzheng.xutils3.Contants.MyContonts;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@ContentView(R.layout.activity_big_image_show)
public class BigImageShowActivity extends BaseActivity {
    @ViewInject(R.id.iv)
    private ImageView iv;
    @ViewInject(R.id.btn1)
    private Button btn1;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private int TO_CROP = 0;
    private ImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
//                 .setSize(100,)
                .setIgnoreGif(false)
                .setCrop(true)
                // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
        x.image().bind(iv, getIntent().getStringExtra("url"), imageOptions);
        registerReceiver(_receiver, new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED));
        btn1.setText("设置壁纸123");
    }

    @Event(value = {R.id.btn1, R.id.btn0}, type = View.OnClickListener.class)
    private void setWallper(View view) throws IOException {
        switch (view.getId()) {
            case R.id.btn0:// 裁剪图片
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    iv.setDrawingCacheEnabled(true);
                    Bitmap bitmap = iv.getDrawingCache();// 获取bitmap
                    File file = saveBitmapFile(bitmap);// 获取保存文件
                    iv.setDrawingCacheEnabled(false);
                    startPhotoCrop(Uri.fromFile(file));
                } else {
                    Toast.makeText(getApplicationContext(),
                            "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn1:// 设置壁纸
                iv.setDrawingCacheEnabled(true);
                Bitmap bm = iv.getDrawingCache();
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                wallpaperManager.setBitmap(bm);
                wallpaperManager.suggestDesiredDimensions(getWidth(), getHight());
                iv.setDrawingCacheEnabled(false);
                break;
            default:
                break;
        }

    }


    /**
     * 保存ImageView到
     *
     * @param bitmap
     * @return
     */
    public File saveBitmapFile(Bitmap bitmap) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(getSelectFile()));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return getSelectFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoCrop(Uri uri) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", (int) (iv.getWidth() * 0.7));// 裁剪框比例
        intent.putExtra("aspectY", (int) (iv.getHeight() * 0.7));
        intent.putExtra("outputX", 1024);// 输出图片大小
        intent.putExtra("outputY", 1024 * iv.getHeight() / iv.getWidth());
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getOutputFile()));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());// 返回格式
        this.startActivityForResult(intent, TO_CROP);
    }

    private boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    private File getSelectFile() {
        if (isSDCARDMounted()) {
            File f = new File(MyContonts.cacheDir,
                    "test1.png");
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Toast.makeText(this, "errot", Toast.LENGTH_LONG)
                        .show();
            }
            return f;
        } else {
            return null;
        }
    }

    private File getOutputFile() {
        if (isSDCARDMounted()) {
            File f = new File(MyContonts.cacheDir,
                    "test2.png");
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Toast.makeText(this, "errot", Toast.LENGTH_LONG)
                        .show();
            }
            return f;
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TO_CROP) {
            if (resultCode == -1) {
                iv.setImageDrawable(null);
                iv.setImageURI(Uri.fromFile(getOutputFile()));
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        getHandler().sendEmptyMessage(0);
//                    }
//                }.start();
            } else if (resultCode == 0) {
                return;
            }
        }
    }

    //
    private BroadcastReceiver _receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_WALLPAPER_CHANGED)) {
                Toast.makeText(BigImageShowActivity.this, "已更改墙纸", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(_receiver);
//
//    }
//
    @Override
    public void OnHandlerListener(Object object) {
        super.OnHandlerListener(object);
        Message msg = (Message) object;
        if (msg.what == 0) {
//            x.image().bind(iv, Uri.fromFile(getOutputFile()).toString());
//            iv.setImageURI(Uri.fromFile(getSelectFile()));// 不执行这个操作无法加载新图片
//            iv.notify();
            iv.setImageDrawable(null);
            iv.setImageURI(Uri.fromFile(getOutputFile()));
//            iv.invalidate();
//            iv.notify();
        }
    }


}
