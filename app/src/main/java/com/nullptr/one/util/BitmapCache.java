package com.nullptr.one.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/17
 * @DESCRIPTION
 */
public class BitmapCache implements ImageCache {
    private static final String CACHE_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/ONE";   //本地缓存路径
    private static LruCache<String, Bitmap> sMemoryCache;

    public BitmapCache(){
        //初始化LruCache
        if (sMemoryCache == null) {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);  //将最大内存换算为kb(原来以B为单位)
            final int cacheSize = maxMemory / 8;    //取1/8的内存为LruCache的大小
            //计算LruCache空间大小
            sMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
    }


    @Override
    public Bitmap getBitmap(String url) {
        boolean isLoaded = false;
        if (!isLoaded) {
            //先从内存拿
            Bitmap bitmap = getBitmapFromMemoryCache(url);
            if (bitmap != null) {
                isLoaded = true;
                return bitmap;
            }
        }
        if(!isLoaded) {
            //再从本地拿
            Bitmap bitmap = getBitmapFromLocal(url);
            if (bitmap != null) {
                isLoaded = true;
                return bitmap;
            }
        }
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        addBitmapToMemoryCache(url,bitmap);
        addBitmapToLocal(url,bitmap);
    }

    /**
     * 内存缓存
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            sMemoryCache.put(key, bitmap);  //图片没有放入时将图片放入内存
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return sMemoryCache.get(key);   //从内存取出对应图片
    }

    /**
     * 本地缓存
     */
    public Bitmap getBitmapFromLocal(String url) {
        String fileName = null;
        try {
            //进行MD5加密的原因：不让一些特殊的url影响文件的存储
            //同时让接口不被用户看到
            //把图片的url当做文件名,并进行MD5加密
            fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, fileName);
            Bitmap bitmap = null;
            if (file.exists()) {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                addBitmapToMemoryCache(url, bitmap); //添加到内存缓存
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addBitmapToLocal(String url, Bitmap bitmap) {
        try {
            //进行MD5加密的原因：不让一些特殊的url影响文件的存储
            //同时让接口不被用户看到
            //把图片的url当做文件名,并进行MD5加密
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, fileName);

            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
