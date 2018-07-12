package com.nullptr.one.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.widget.TextView;


/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/23
 * @DESCRIPTION Html解析类
 */
public class HtmlPraser {

    private static HtmlPraser sInstance = new HtmlPraser();
    private static Handler mUiHandler = new Handler(Looper.getMainLooper());

    public static HtmlPraser getInstance() {
        return sInstance;
    }

    public void setHtml(final TextView textView, final String html) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Spanned text = Html.fromHtml(html, new MyImageGetter(), null);
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(text);
                    }
                });
            }
        }).start();
    }

    private class MyImageGetter implements ImageGetter {

        @Override
        public Drawable getDrawable(final String url) {
            boolean isLoaded = false;
            Drawable drawable = null;
            //内存缓存
            Bitmap memoryBitmap = ImageLoader.getInstance().getBitmapFromMemoryCache(url);
            if (memoryBitmap != null) {
                isLoaded = true;
                drawable = new BitmapDrawable(memoryBitmap);
                drawable.setBounds(0, 0, memoryBitmap.getWidth(), memoryBitmap.getHeight());
            }
            //本地缓存
            if (!isLoaded) {
                Bitmap localBitmap = ImageLoader.getInstance().getBitmapFromLocal(url);
                if (localBitmap != null) {
                    isLoaded = true;
                    drawable = new BitmapDrawable(localBitmap);
                    drawable.setBounds(0, 0, localBitmap.getWidth(), localBitmap.getHeight());
                }
            }
            //网络缓存
            if (!isLoaded) {
                Bitmap netBitmap = ImageLoader.getInstance().getBitmapFromNet(url);
                if (netBitmap != null) {
                    isLoaded = true;
                    drawable = new BitmapDrawable(netBitmap);
                    drawable.setBounds(0, 0, netBitmap.getWidth(), netBitmap.getHeight());
                }
            }
            return drawable;
        }

    }
}
