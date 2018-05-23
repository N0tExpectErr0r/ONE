package com.nullptr.one.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
import com.nullptr.one.MyApplication;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/23
 * @DESCRIPTION
 */
public class HtmlPraser {
    private static HtmlPraser sInstance = new HtmlPraser();

    public static HtmlPraser getInstance() {
        return sInstance;
    }

    private static ImageGetter sImageGetter = new ImageGetter() {
        @Override
        public Drawable getDrawable(final String url) {
            Drawable drawable = null;
            boolean isLoaded = false;
            //内存缓存
            Bitmap bitmap = ImageLoader.getInstance().getBitmapFromMemoryCache(url);
            if (bitmap != null && !isLoaded) {
                drawable = new BitmapDrawable(MyApplication.getContext().getResources(),bitmap);
                isLoaded = true;
            }
            //本地缓存
            bitmap = ImageLoader.getInstance().getBitmapFromLocal(url);
            if (bitmap != null && !isLoaded) {
                drawable = new BitmapDrawable(MyApplication.getContext().getResources(),bitmap);
                isLoaded = true;
            }
            //网络缓存
            bitmap = ImageLoader.getInstance().getBitmapFromNet(url);
            if (bitmap != null && !isLoaded) {
                drawable = new BitmapDrawable(MyApplication.getContext().getResources(),bitmap);
                isLoaded = true;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    };

    public Spanned prase(String html){
        return Html.fromHtml(html,sImageGetter,null);
    }
}
