package com.example.administrator.cbwapplication.home.fragment;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.cbwapplication.R;
import com.example.administrator.cbwapplication.application.MyApplication;
import com.example.administrator.cbwapplication.common.fragment.BaseFragment;
import com.example.administrator.cbwapplication.home.entity.AppPackageInfo;
import com.example.administrator.cbwapplication.util.SystemUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 功能:第二tab页<br>
 * Created by cbw on 2015/7/21.
 */

@ContentView(R.layout.tab02)
public
class
FrdFragment extends BaseFragment {

    @ViewInject(R.id.my_img)
    private ImageView imageView;

    @ViewInject(R.id.tv_fd)
    private TextView tv_fd;

    String imageUrl = "http://img3.imgtn.bdimg.com/it/u=2083960344,1017807441&fm=21&gp=0.jpg";

    @Override
    public void init() {
        MyApplication.disPlayUrlImage(imageView, imageUrl);
        tv_fd.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_fd:
                List<AppPackageInfo> netControlList = SystemUtils.getAllAppPackageList(getContext());
                if (netControlList != null) {
                    for (int i = 0; i < netControlList.size(); i++) {
                        Log.i("cbw", "应用名:" + netControlList.get(i).getAppName() + "    包名:" + netControlList.get(i).getPackageName());
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void handleReturnMessage(Message msg) {

    }
}
