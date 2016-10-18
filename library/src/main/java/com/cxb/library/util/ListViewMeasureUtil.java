package com.cxb.library.util;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 功能:GridView,ListView 高度计算 用于GridView,ListView内容显示不全
 * Created by cbw on 2016/1/29.
 */
public
class ListViewMeasureUtil {
    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 重新计算GridView高度 比边其他滚动条使listView显示不全 让GridView显示全部内容 注： 本方法调用完毕后最好
     * 手动调用一次adapter.notifyDataSetChanged();
     *
     * @param gridView 要调整的GridView
     * @author lilw
     * @CrateTime 2013-4-2
     */
    @SuppressLint("NewApi")
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        if (gridView == null)
            return;
        ListAdapter listAdapter = gridView.getAdapter();
        int num = gridView.getNumColumns();
        // num = gridView.get
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int count = 1;
        if (listAdapter.getCount() % num == 0) {
            count = listAdapter.getCount() / num;
        } else {
            count = (int) Math.ceil((double) listAdapter.getCount()
                    / (double) num);
        }
        // Log.i("test","count == "+count);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            if (totalHeight < listItem.getMeasuredHeight()) {
                totalHeight = listItem.getMeasuredHeight() + 10;
            }
        }
        // Log.i("test","count == "+totalHeight);
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // params.height = totalHeight*count + listAdapter.getCount();
        params.height = count == 0 ? 0 : (totalHeight * count)
                + gridView.getPaddingTop() + gridView.getPaddingBottom();
        gridView.setLayoutParams(params);
    }
}
