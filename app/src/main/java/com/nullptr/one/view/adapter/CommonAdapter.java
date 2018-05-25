package com.nullptr.one.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nullptr.one.view.ui.LoadMoreListView;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/9
 * @DESCRIPTION 通用的ListView Adapter
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext; //上下文，用于创建LayoutInflater
    protected List<T> mDatas;   //数据(T为保存要显示在内容中的bean)
    protected LayoutInflater mInflater;
    protected int mLayoutId;    //item布局id

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mLayoutId = layoutId;
    }

    //改变当前数据集
    public void setDataList(List<T> dataList) {
        mDatas = dataList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayoutId, position);
        convert(holder, mDatas.get(position));

        return holder.getConvertView();
    }

    //将getView方法继续抽，抽掉return语句以及初始化holder语句。
    public abstract void convert(ViewHolder holder, T t);
}