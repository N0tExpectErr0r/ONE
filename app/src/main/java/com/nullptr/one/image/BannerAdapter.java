package com.nullptr.one.image;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nullptr.one.R;
import com.nullptr.one.util.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/21
 * @DESCRIPTION 轮播图adapter
 */
public class BannerAdapter extends PagerAdapter {

    private List<ImageDetail> mImageList;
    private List<View> mViewList;
    private Context mContext;

    public BannerAdapter(Context context, List<ImageDetail> imageList) {
        mImageList = imageList;
        mViewList = new ArrayList<>(mImageList.size());
        mContext = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageDetail image = mImageList.get(position % mImageList.size());
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_bannerpager, null);
        ImageView imageView = view.findViewById(R.id.bannerpager_iv_image);
        TextView title = view.findViewById(R.id.bannerpager_tv_title);
        TextView content = view.findViewById(R.id.bannerpager_tv_content);

        ImageLoader.getInstance().loadImg(imageView, image.getImageURL());
        title.setText(image.getTitle());
        content.setText(image.getContent());
        mViewList.add(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position % mImageList.size()));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
