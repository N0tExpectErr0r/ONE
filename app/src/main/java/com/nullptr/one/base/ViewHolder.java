package com.nullptr.one.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.util.BitmapCache;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/9
 * @DESCRIPTION 通用的ListView ViewHolder
 */
public class ViewHolder {

    //SparseArray实际上是一个Map，它的key已默认为Integer，value为Object
    //此处使用它的原因是它的效率比HashMap要高，且此处需要的key正好为Integer类型
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent,
            int layoutId, int position) {
        if (convertView == null) {
            //如果还没有创建ViewHolder，创建ViewHolder
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            //已创建ViewHolder，则ViewHolder在convertView的Tag中，通过getTag获取ViewHolder
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;    //更新位置
            return holder;
        }
    }

    //通过viewId获取View
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            //如果这个控件没有放入过，放入。
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    //设置TextView的值
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    //设置ImageView的图片(resource)
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    //设置ImageView的图片(bitmap)
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }


    //设置ImageView的图片(drawable)
    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    //设置ImageView的图片(url)
    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        imageView.setTag(url);

        ImageLoader loader = new ImageLoader(
                ContextApplication.getHttpQueues(), new BitmapCache());

        ImageListener listener = com.android.volley.toolbox.ImageLoader
                .getImageListener(imageView, R.drawable.mock, R.drawable.mock);

        loader.get(url, listener, imageView.getWidth(), imageView.getHeight());
        return this;
    }

    public int getPosition() {
        return mPosition;
    }
}
