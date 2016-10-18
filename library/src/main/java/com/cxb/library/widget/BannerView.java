package com.cxb.library.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxb.library.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * 功能:自定义图片 无限自动轮播也可手动滑动,小圆点加文字描述 类似优酷首页<br>
 * Created by cbw on 2016/1/29.
 */
public
class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener,
        View.OnClickListener {
    public class Data {

        public String ThumbPath;
        public String TargetUrl;
        public String PerformName = "";

    }

    public static final int BANNER_DELAY_MILLIS = 3000;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private int mCurrentPointIndex;
    private List<Data> data;
    private TextView tvDesc;
    private int position;

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        setBackgroundResource(R.mipmap.nocar);
        mViewPager = new InnerViewPager(getContext());
        addView(mViewPager);
        mLinearLayout = new LinearLayout(getContext());
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLinearLayout.setBackgroundColor(0x00000000);
        // mLinearLayout.setPadding(25, 0, 30, 0);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, 100, Gravity.BOTTOM);
        addView(mLinearLayout, params);
    }

    public void setData(List<Data> data) {
        this.data = data;
        for (Data d : data) {
            d.PerformName = "";
        }
        initPoint();
        initViewPager();

    }

    public void pause() {
        mViewPager.removeCallbacks(task);
    }

    public void resume() {
        mViewPager.removeCallbacks(task);
        mViewPager.postDelayed(task, BANNER_DELAY_MILLIS);
    }

    private void initViewPager() {
        mViewPager.removeCallbacks(task);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView iv = new ImageView(getContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String url = data.get(position % data.size()).ThumbPath;
                ImageLoader.getInstance().displayImage(
                        url,
                        iv,
                        new DisplayImageOptions.Builder().cacheInMemory(true)
                                .cacheOnDisk(true).build());
                container.addView(iv);

                iv.setOnClickListener(BannerView.this);
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView((View) object);
            }
        });

        mViewPager.setCurrentItem(0xffff * data.size());

        mViewPager.setOnPageChangeListener(this);
        mViewPager.postDelayed(task, BANNER_DELAY_MILLIS);

        tvDesc.setText(data.get(0).PerformName);

    }

    private void initPoint() {
        mLinearLayout.removeAllViews();
        LinearLayout.LayoutParams leftRightLayoutParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        tvDesc = new TextView(getContext());
        tvDesc.setGravity(Gravity.CENTER_VERTICAL);
        mLinearLayout.addView(tvDesc, leftRightLayoutParams);
        for (int i = 0; i < data.size(); i++) {
            LinearLayout.LayoutParams pointLayoutParams = new LinearLayout.LayoutParams(
                    20, 20);
            pointLayoutParams.setMargins(10, 10, 10, 10);
            pointLayoutParams.gravity = Gravity.CENTER_VERTICAL;
            View child = new View(getContext());
            child.setBackgroundResource(R.drawable.banner_point);
            if (i == 0) {
                child.setEnabled(false);
            }
            mLinearLayout.addView(child, pointLayoutParams);

        }
        // mLinearLayout.addView(new View(getContext()), leftRightLayoutParams);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position % data.size();
        tvDesc.setText(data.get(this.position).PerformName);
        View child = mLinearLayout.getChildAt(mCurrentPointIndex + 1);
        child.setEnabled(true);
        mCurrentPointIndex = this.position;
        child = mLinearLayout.getChildAt(mCurrentPointIndex + 1);
        child.setEnabled(false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    Runnable task = new Runnable() {
        @Override
        public void run() {
            if (mViewPager != null) {
                mViewPager.removeCallbacks(task);
                int position = mViewPager.getCurrentItem() + 1;
                mViewPager.setCurrentItem(position, true);
                mViewPager.postDelayed(task, BANNER_DELAY_MILLIS);
            }
        }
    };

    private class InnerViewPager extends ViewPager {
        private float lastX;
        private float lastY;

        InnerViewPager(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getX();
                    lastY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    mViewPager.postDelayed(task, BANNER_DELAY_MILLIS);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mViewPager.removeCallbacks(task);
                    if (Math.abs(event.getX() - lastX) < Math.abs(event.getY()
                            - lastY)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                    break;
                default:
                    mViewPager.removeCallbacks(task);
                    break;
            }

            return super.onTouchEvent(event);
        }

    }

    @Override
    public void onClick(View arg0) {

        if (data != null && data.size() > 0) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(data.get(position).TargetUrl);
            intent.setData(content_url);
            getContext().startActivity(intent);
        }

    }
}
