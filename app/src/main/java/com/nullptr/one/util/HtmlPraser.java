package com.nullptr.one.util;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;


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

    public Spanned prase(String html) {

        return Html.fromHtml(html, new MyImageGetter(), null);
    }

    private class MyImageGetter implements ImageGetter {

        @Override
        public Drawable getDrawable(final String url) {
            //图片处理
            return null;
        }
    }
}
