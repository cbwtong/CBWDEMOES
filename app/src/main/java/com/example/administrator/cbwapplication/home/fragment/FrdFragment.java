package com.example.administrator.cbwapplication.home.fragment;


import android.os.Message;

import android.view.View;


import android.widget.ImageView;

import com.example.administrator.cbwapplication.R;

import com.example.administrator.cbwapplication.application.MyApplication;
import com.example.administrator.cbwapplication.common.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;


import org.xutils.view.annotation.ViewInject;

/**
 * 功能:第二tab页<br>
 * Created by cbw on 2015/7/21.
 */

@ContentView(R.layout.tab02)
public
class FrdFragment extends BaseFragment {

    @ViewInject(R.id.my_img)
    private ImageView imageView;

    String imageUrl = "http://img3.imgtn.bdimg.com/it/u=2083960344,1017807441&fm=21&gp=0.jpg";

    @Override
    public void init() {
        MyApplication.disPlayUrlImage(imageView, imageUrl);
    }

    @Override
    public void onClickEvent(View view) {

    }

    @Override
    public void handleReturnMessage(Message msg) {

    }
}
