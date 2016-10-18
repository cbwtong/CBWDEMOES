package com.example.administrator.cbwapplication.home.activity;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.common.activity.BaseActivity;
import com.example.administrator.cbwapplication.home.fragment.AddressFragment;
import com.example.administrator.cbwapplication.home.fragment.FrdFragment;
import com.example.administrator.cbwapplication.home.fragment.SettingFragment;
import com.example.administrator.cbwapplication.home.fragment.WeiXinFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:程序主界面  ViewPager与FragmentPagerAdapter实现首页tab导航 <br>
 * Created by cbw on 2015/12/14.
 */

@ContentView(R.layout.activity_main)
public
class MainActivity extends BaseActivity {

    @ViewInject(R.id.id_viewpager)
    ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    //TAB
    @ViewInject(R.id.id_tab_weiXin)
    LinearLayout mTabWeiXin;

    @ViewInject(R.id.id_tab_frd)
    LinearLayout mTabFrd;

    @ViewInject(R.id.id_tab_address)
    LinearLayout mTabAddress;

    @ViewInject(R.id.id_tab_set)
    LinearLayout mTabSet;

    //ImageButton
    @ViewInject(R.id.tab_weiXin_img)
    ImageButton mWeiXinImg;

    @ViewInject(R.id.tab_frd_img)
    ImageButton mFrdImg;

    @ViewInject(R.id.tab_address_img)
    ImageButton mAddressImg;

    @ViewInject(R.id.tab_set_img)
    ImageButton mSetImg;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;


    /**
     * 初始化工作
     */
    @Override
    public void init() {

        mFragments = new ArrayList<Fragment>();

        mTab01 = new WeiXinFragment();
        mTab02 = new FrdFragment();
        mTab03 = new AddressFragment();
        mTab04 = new SettingFragment();

        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);
        mFragments.add(mTab04);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mTabWeiXin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSet.setOnClickListener(this);

    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.id_tab_weiXin:
                setSelect(0);
                break;
            case R.id.id_tab_frd:
                setSelect(1);
                break;
            case R.id.id_tab_address:
                setSelect(2);
                break;
            case R.id.id_tab_set:
                setSelect(3);
                break;
            default:
                break;
        }
    }

    /**
     * 切换fragment内容区域
     *
     * @param i
     */
    private void setSelect(int i) {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    /**
     * 切换tab项
     *
     * @param i
     */
    private void setTab(int i) {
        resetImgs();
        //设置图片为亮色
        //切换内容区域
        switch (i) {
            case 0:
                mWeiXinImg.setImageResource(R.mipmap.tab_weixin_pressed);
                break;
            case 1:
                mFrdImg.setImageResource(R.mipmap.tab_find_frd_pressed);
                break;
            case 2:
                mAddressImg.setImageResource(R.mipmap.tab_address_pressed);
                break;
            case 3:
                mSetImg.setImageResource(R.mipmap.tab_settings_pressed);
                break;
            default:
                break;
        }

    }

    /**
     * 初始化底部图片
     * 切换为暗色
     */
    private void resetImgs() {
        mWeiXinImg.setImageResource(R.mipmap.tab_weixin_normal);
        mFrdImg.setImageResource(R.mipmap.tab_find_frd_normal);
        mAddressImg.setImageResource(R.mipmap.tab_address_normal);
        mSetImg.setImageResource(R.mipmap.tab_settings_normal);
    }

    @Override
    public void handleReturnMessage(Message msg) {

    }
}
