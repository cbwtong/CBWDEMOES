package com.example.administrator.cbwapplication.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> datas, int itemLayoutId) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDatas = datas;
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        // 列表数量
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        // 获取某一项item
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // 获取某一项id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final BaseViewHolder viewHolder = BaseViewHolder.getViewHolder(
                mContext, convertView, parent, mItemLayoutId, position);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getCovertView();
    }

    public abstract void convert(BaseViewHolder viewHolder, T item, int position);
}
