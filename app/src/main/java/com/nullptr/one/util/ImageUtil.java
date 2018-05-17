package com.nullptr.one.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.nullptr.one.R;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/11
 * @DESCRIPTION 图片加载工具类
 */
public class ImageUtil {

    /**
     * 将url对应的Bitmap应用于ImageView 原来想用于直接加载图片，结果发现会导致图片错乱并且每次滑动都会加载
     *
     * @param imageView 要应用的imageView
     * @param url 对应的url
     */
    public static void loadImg(final ImageView imageView, final String url) {
        //由于图片加载比较慢，所以先给图片一个mock图像来顶替，直到加载出来
        //否则会导致ListView滑动时出现图片混乱的问题
        imageView.setImageResource(R.drawable.mock);
        //获取ImageView所在的Activity
        final Activity activity = (Activity) imageView.getContext();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = ImageUtil.getImageBitmap(url);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在UI线程应用Bitmap
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    /**
     * 获取网络图片对应Bitmap
     *
     * @param url 网络图片url
     * @return 对应的Bitmap
     */
    public static Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
