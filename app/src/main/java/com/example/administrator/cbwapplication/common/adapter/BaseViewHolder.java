package com.example.administrator.cbwapplication.common.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.cbwapplication.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public
class BaseViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    protected ImageLoader mImageLoader;
    protected DisplayImageOptions mDisplayImageOptions;

    private BaseViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
        this.mContext = context;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.default_picture)
                .showImageForEmptyUri(R.mipmap.default_picture)
                .showImageOnFail(R.mipmap.default_picture)
                .resetViewBeforeLoading(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        mImageLoader = ImageLoader.getInstance();
    }

    private BaseViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position, int defaultPicId, int emptyPicId, int loadFailPicId) {
        this.mContext = context;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(defaultPicId).showImageForEmptyUri(emptyPicId)
                .showImageOnFail(loadFailPicId).resetViewBeforeLoading(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 获取viewHolder对象
     *
     * @param context
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static BaseViewHolder getViewHolder(Context context,
                                               View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new BaseViewHolder(context, parent, layoutId, position);
        }
        return (BaseViewHolder) convertView.getTag();
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, String text) {
        TextView textView = (TextView) getView(viewId);
        textView.setText(text);
    }

    /**
     * 为TextView设置字体颜色
     *
     * @param viewId
     * @param text
     */
    public void setTextColor(int viewId, int colorId) {
        TextView textView = (TextView) getView(viewId);
        textView.setTextColor(colorId);
    }

    /**
     * 通过图片的id为ImageView设置图片
     *
     * @param viewId
     * @param resourceId
     */
    public void setImageResource(int viewId, int resourceId) {
        ImageView imageView = (ImageView) getView(viewId);
        imageView.setImageResource(resourceId);
    }

    /**
     * 通过图片的bitmap为ImageView设置图片
     *
     * @param viewId
     * @param resourceId
     */
    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = (ImageView) getView(viewId);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 通过图片的url地址为ImageView设置图片
     *
     * @param viewId
     * @param resourceId
     */
    public void setImageByUrl(int viewId, String url) {
        ImageView imageView = (ImageView) getView(viewId);
        mImageLoader.displayImage(url, imageView, mDisplayImageOptions);
    }

    /**
     * 通过图片的id为ImageView设置背景
     *
     * @param viewId
     * @param resourceId
     */
    public void setImageBackGroundResource(int viewId, int resourceId) {
        ImageView imageView = (ImageView) getView(viewId);
        imageView.setBackgroundResource(resourceId);
    }

    /**
     * 设置控件的显示和隐藏
     *
     * @param viewId
     * @param visibility
     */
    public void setVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.append(viewId, view);
        }
        return (T) view;
    }

    public View getCovertView() {
        return mConvertView;
    }

    public Context getContext() {
        return mContext;
    }
}
