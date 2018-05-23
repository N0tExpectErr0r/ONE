package com.nullptr.one.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;
import com.nullptr.one.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public class ImageLoader {

    private static ImageLoader sInstance = new ImageLoader();

    //使用LruCache的原因:当缓存空间满了的时候，将最近最少使用的数据从缓存空间中删除以增加可用的缓存空间来缓存新内容。
    //内部其实是由LinkHashMap实现

    private static LruCache<String, Bitmap> sMemoryCache;

    private static final String CACHE_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/ONE";   //本地缓存路径

    public ImageLoader() {
        if (sMemoryCache == null) {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            sMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
    }

    public static ImageLoader getInstance() {
        return sInstance;
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

            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            addBitmapToMemoryCache(url, bitmap); //添加到内存缓存
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

    /**
     * 网络缓存
     */
    public Bitmap getBitmapFromNet(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is); //获取网络图片
            addBitmapToMemoryCache(url, bitmap); //添加到内存缓存
            addBitmapToLocal(url, bitmap);   //添加到本地缓存
            is.close();
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public void loadImg(final ImageView imageView, final String url) {

        imageView.setImageResource(R.drawable.mock);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //内存缓存
                boolean isLoaded = false;
                final Bitmap memoryBitmap = getBitmapFromMemoryCache(url);
                if (memoryBitmap != null && !isLoaded) {
                    isLoaded = true;
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(memoryBitmap);
                        }
                    });
                }
                //本地缓存
                final Bitmap localBitmap = getBitmapFromLocal(url);
                if (localBitmap != null && !isLoaded) {
                    isLoaded = true;
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(localBitmap);
                        }
                    });
                }
                //网络缓存
                final Bitmap netBitmap = getBitmapFromNet(url);
                if (netBitmap != null && !isLoaded) {
                    isLoaded = true;
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(netBitmap);
                        }
                    });
                }
            }
        }).start();
    }

}
