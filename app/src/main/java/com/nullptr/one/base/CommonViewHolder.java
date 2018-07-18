package com.nullptr.one.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.util.BitmapCache;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View>  mViews;
    private View mItemView;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mViews = new SparseArray<>();
    }

    //通过viewId获取View
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            //如果这个控件没有放入过，放入。
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //设置TextView的值
    public CommonViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    //设置ImageView的图片(resource)
    public CommonViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    //设置ImageView的图片(bitmap)
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }


    //设置ImageView的图片(drawable)
    public CommonViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    //设置ImageView的图片(url)
    public CommonViewHolder setImageUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        imageView.setTag(url);

        ImageLoader loader = new ImageLoader(
                ContextApplication.getHttpQueues(), new BitmapCache());

        ImageListener listener = com.android.volley.toolbox.ImageLoader
                .getImageListener(imageView, R.drawable.mock, R.drawable.mock);

        loader.get(url, listener, imageView.getWidth(), imageView.getHeight());
        return this;
    }

}
